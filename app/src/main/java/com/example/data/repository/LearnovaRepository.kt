package com.example.data.repository

import android.content.Context
import com.example.ai.Needle3Runtime
import com.example.ai.RAGEngine
import com.example.data.db.ChatMessageEntity
import com.example.data.db.DocumentChunkEntity
import com.example.data.db.DocumentEntity
import com.example.data.db.LearnovaDatabase
import com.example.data.db.QuizEntity
import com.example.data.db.WorkspaceEntity
import com.example.model.ChatMessage
import com.example.model.Citation
import com.example.model.CloudProviderConfig
import com.example.model.Document
import com.example.model.DocumentChunk
import com.example.model.InferenceMode
import com.example.model.MessageSender
import com.example.model.Quiz
import com.example.model.QuizAttempt
import com.example.model.QuizQuestion
import com.example.model.Workspace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LearnovaRepository(context: Context) {

    private val db = LearnovaDatabase.getDatabase(context)
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _inferenceMode = MutableStateFlow(InferenceMode.LOCAL_ONLY)
    val inferenceMode: StateFlow<InferenceMode> = _inferenceMode.asStateFlow()

    private val _currentWorkspaceId = MutableStateFlow("cs-301")
    val currentWorkspaceId: StateFlow<String> = _currentWorkspaceId.asStateFlow()

    private val _cloudProviderConfig = MutableStateFlow(CloudProviderConfig())
    val cloudProviderConfig: StateFlow<CloudProviderConfig> = _cloudProviderConfig.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _activeQuiz = MutableStateFlow<Quiz?>(null)
    val activeQuiz: StateFlow<Quiz?> = _activeQuiz.asStateFlow()

    private val _lastQuizAttempt = MutableStateFlow<QuizAttempt?>(null)
    val lastQuizAttempt: StateFlow<QuizAttempt?> = _lastQuizAttempt.asStateFlow()

    init {
        scope.launch {
            seedInitialDataIfEmpty()
        }
    }

    fun setInferenceMode(mode: InferenceMode) {
        _inferenceMode.value = mode
    }

    fun setCurrentWorkspace(id: String) {
        _currentWorkspaceId.value = id
        loadMessagesForWorkspace(id)
    }

    fun updateCloudConfig(config: CloudProviderConfig) {
        _cloudProviderConfig.value = config
    }

    val workspaces: Flow<List<Workspace>> = db.workspaceDao().getAllWorkspaces().map { list ->
        list.map { it.toDomain() }
    }

    fun getDocuments(workspaceId: String): Flow<List<Document>> {
        return db.documentDao().getDocumentsByWorkspace(workspaceId).map { list ->
            list.map { it.toDomain() }
        }
    }

    private fun loadMessagesForWorkspace(workspaceId: String) {
        scope.launch {
            if (_chatMessages.value.isEmpty() && workspaceId == "cs-301") {
                _chatMessages.value = defaultSampleChat()
            }
        }
    }

    fun sendMessage(userText: String) {
        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}_u",
            workspaceId = _currentWorkspaceId.value,
            sender = MessageSender.USER,
            content = userText,
            timestamp = "09:42 AM"
        )
        _chatMessages.value = _chatMessages.value + userMsg

        // Trigger Local Only or Cloud AI response
        scope.launch {
            val answerText = Needle3Runtime.buildAcademicAnswer(userText, 14)
            val citations = listOf(
                Citation(
                    id = "c1",
                    documentTitle = "Lecture 04: AVL Trees",
                    slideNumber = 14,
                    pageNumber = 14,
                    confidencePercent = 98.4f,
                    excerpt = "\"A Left-Right rotation consists of a single left rotation around the left child followed by a single right rotation around the node itself. This restores the AVL invariant in O(1) constant time.\"",
                    professorQuote = "Prof. Vance's Lecture Transcription • 38m:12s: \"Pay close attention to Lemma 4.1 when preparing for Midterm 1.\""
                ),
                Citation(
                    id = "c2",
                    documentTitle = "Textbook Ch. 4 • Sec 4.3",
                    slideNumber = 112,
                    pageNumber = 112,
                    confidencePercent = 94.2f,
                    excerpt = "Cormen et al. Theorem 13.2: Rebalancing bounds on secondary permutations."
                )
            )

            val isLocal = _inferenceMode.value == InferenceMode.LOCAL_ONLY
            val aiMsg = ChatMessage(
                id = "msg_${System.currentTimeMillis()}_a",
                workspaceId = _currentWorkspaceId.value,
                sender = MessageSender.ASSISTANT,
                content = answerText,
                timestamp = "09:42 AM",
                latencyFormatted = if (isLocal) "1.4s" else "0.8s",
                tokensPerSec = if (isLocal) 48 else 62,
                modelName = if (isLocal) "Needle3" else _cloudProviderConfig.value.providerName,
                isZeroEgress = isLocal,
                citations = citations,
                codeSnippet = "// Double Rotation: Left-Right (LR)\nNode rotateLeftRight(Node node) {\n    node.left = rotateLeft(node.left);\n    return rotateRight(node);\n}",
                codeLanguage = "avl_tree.java",
                formula = "BF(v) = height(v.left) - height(v.right) in {-1, 0, +1}"
            )
            _chatMessages.value = _chatMessages.value + aiMsg
        }
    }

    fun generateQuiz(topic: String, count: Int, difficulty: String): Quiz {
        val questions = createSampleQuizQuestions()
        val quiz = Quiz(
            id = "quiz_${System.currentTimeMillis()}",
            workspaceId = _currentWorkspaceId.value,
            title = "AVL Trees & Rebalancing Rotations",
            topic = topic,
            difficulty = difficulty,
            questionCount = count,
            questions = questions,
            createdAt = "Just now"
        )
        _activeQuiz.value = quiz
        return quiz
    }

    fun submitQuizAnswers(quizId: String, selectedAnswers: Map<Int, Int>) {
        val currentQuiz = _activeQuiz.value ?: return
        var correctCount = 0
        for ((idx, selected) in selectedAnswers) {
            val q = currentQuiz.questions.getOrNull(idx)
            if (q != null && q.correctIndex == selected) {
                correctCount++
            }
        }
        val score = if (currentQuiz.questions.isNotEmpty()) (correctCount * 100) / currentQuiz.questions.size else 80
        val attempt = QuizAttempt(
            quizId = quizId,
            score = correctCount,
            total = currentQuiz.questions.size,
            timeSpentFormatted = "14m 20s",
            pacingFormatted = "1m 26s",
            citationsValidPercent = 100,
            answers = selectedAnswers
        )
        _lastQuizAttempt.value = attempt
        _activeQuiz.value = currentQuiz.copy(lastScorePercent = score)
    }

    private suspend fun seedInitialDataIfEmpty() {
        val existing = db.workspaceDao().getWorkspaceById("cs-301")
        if (existing == null) {
            val wsList = listOf(
                WorkspaceEntity(
                    id = "cs-301",
                    title = "Data Structures & Algorithms",
                    courseCode = "CS-301",
                    category = "Active Semester",
                    description = "Binary Search Trees, AVL Trees, Invariants, and Graph Algorithms.",
                    documentCount = 8,
                    flashcardCount = 32,
                    quizCount = 14,
                    lastStudied = "Binary Search Trees & AVL • 2h ago",
                    isPinned = true
                ),
                WorkspaceEntity(
                    id = "rm-502",
                    title = "Research Methods & Thesis",
                    courseCode = "RM-502",
                    category = "Honors Thesis",
                    description = "Quantitative Methodology, Qualitative Analysis, IEEE 2024 Paper Synthesis.",
                    documentCount = 4,
                    flashcardCount = 18,
                    quizCount = 3,
                    lastStudied = "Drafting literature review notes • IEEE 2024",
                    isPinned = true
                ),
                WorkspaceEntity(
                    id = "cs-304",
                    title = "Operating Systems & Concurrency",
                    courseCode = "CS-304",
                    category = "Core Module",
                    description = "Process Synchronization, Mutexes, Semaphores, and Virtual Memory Paging.",
                    documentCount = 6,
                    flashcardCount = 20,
                    quizCount = 8,
                    lastStudied = "Semaphore & Mutex lecture sync ready",
                    isPinned = false
                ),
                WorkspaceEntity(
                    id = "cs-308",
                    title = "Database Management Systems",
                    courseCode = "CS-308",
                    category = "Elective",
                    description = "Relational Algebra, SQL, 3NF & BCNF Normalization, and B+ Trees.",
                    documentCount = 5,
                    flashcardCount = 15,
                    quizCount = 6,
                    lastStudied = "All 3NF & BCNF exercises indexed",
                    isPinned = false
                )
            )
            db.workspaceDao().insertWorkspaces(wsList)

            // Seed documents for CS-301
            val docs = listOf(
                DocumentEntity(
                    id = "doc-1",
                    workspaceId = "cs-301",
                    title = "Lecture 04: AVL Trees",
                    fileName = "CS2040_W04_Slides.pdf",
                    fileType = "PDF",
                    pageCount = 42,
                    fileSizeFormatted = "4.2 MB",
                    isVectorized = true,
                    createdAt = "2 days ago",
                    excerpt = "Section 4.2 Invariant Restoration via Left-Right Double Rotation"
                ),
                DocumentEntity(
                    id = "doc-2",
                    workspaceId = "cs-301",
                    title = "CLRS Chapter 4: Trees",
                    fileName = "CLRS_Ch4_Trees.pdf",
                    fileType = "PDF",
                    pageCount = 68,
                    fileSizeFormatted = "8.1 MB",
                    isVectorized = true,
                    createdAt = "5 days ago",
                    excerpt = "Cormen, Leiserson, Rivest, Stein - Binary Search Trees"
                ),
                DocumentEntity(
                    id = "doc-3",
                    workspaceId = "cs-301",
                    title = "Exam Review Notes",
                    fileName = "Exam_Review_Notes.md",
                    fileType = "MD",
                    pageCount = 12,
                    fileSizeFormatted = "180 KB",
                    isVectorized = true,
                    createdAt = "Yesterday",
                    excerpt = "Midterm Focus: Left-Right (LR) vs Right-Left (RL) rotation triggers."
                )
            )
            db.documentDao().insertDocuments(docs)

            // Seed chunks
            val chunks = listOf(
                DocumentChunkEntity(
                    id = "chk-1",
                    documentId = "doc-1",
                    workspaceId = "cs-301",
                    documentTitle = "CS2040_W04_Slides.pdf",
                    pageNumber = 14,
                    slideNumber = 14,
                    heading = "4.3 AVL Trees: Double Rotations (Left-Right Case)",
                    text = "A Left-Right (LR) rotation consists of a single left rotation around the left child followed by a single right rotation around the node itself. This restores the AVL invariant in O(1) constant time.",
                    tokenCount = 42,
                    confidence = 0.984f
                ),
                DocumentChunkEntity(
                    id = "chk-2",
                    documentId = "doc-1",
                    workspaceId = "cs-301",
                    documentTitle = "CS2040_W04_Slides.pdf",
                    pageNumber = 15,
                    slideNumber = 15,
                    heading = "AVL Balance Factor Invariant Eq 4.3",
                    text = "BF(v) = height(v.left) - height(v.right) in {-1, 0, +1}. When BF(v) = +2 and BF(v.left) = -1, an LR double rotation is mandatory.",
                    tokenCount = 38,
                    confidence = 0.975f
                ),
                DocumentChunkEntity(
                    id = "chk-3",
                    documentId = "doc-1",
                    workspaceId = "cs-301",
                    documentTitle = "CS2040_W04_Slides.pdf",
                    pageNumber = 18,
                    slideNumber = 18,
                    heading = "Deletion Complexity and Rotation Cascades",
                    text = "Unlike tree insertions which restore global invariants with at most one rotation, deletion can decrease subtree height, causing balance factor violations that cascade iteratively up to the root (O(log n) rotations).",
                    tokenCount = 54,
                    confidence = 0.991f
                )
            )
            db.documentChunkDao().insertChunks(chunks)
        }
        _chatMessages.value = defaultSampleChat()
        _activeQuiz.value = Quiz(
            id = "quiz-default",
            workspaceId = "cs-301",
            title = "AVL Trees & Rotations Active Recall",
            topic = "AVL Trees, Rebalancing Invariants & Double Rotations",
            difficulty = "Intermediate",
            questionCount = 10,
            questions = createSampleQuizQuestions(),
            createdAt = "Today",
            lastScorePercent = 80
        )
    }

    private fun defaultSampleChat(): List<ChatMessage> {
        val citations = listOf(
            Citation(
                id = "c1",
                documentTitle = "Lecture 04: AVL Trees",
                slideNumber = 14,
                pageNumber = 14,
                confidencePercent = 98.4f,
                excerpt = "\"A Left-Right rotation consists of a single left rotation around the left child followed by a single right rotation around the node itself. This restores the AVL invariant in O(1) constant time.\"",
                professorQuote = "Prof. Vance's Lecture Transcription • 38m:12s: \"Pay close attention to Lemma 4.1 when preparing for Midterm 1. Compare this with Cormen et al. Theorem 13.2: the inner rotation must happen before the root rotation, or the tree loses binary search invariant validity.\""
            ),
            Citation(
                id = "c2",
                documentTitle = "Textbook Ch. 4 • Sec 4.3",
                slideNumber = 112,
                pageNumber = 112,
                confidencePercent = 94.2f,
                excerpt = "Cormen et al. Theorem 13.2: Rebalancing bounds on secondary permutations."
            )
        )
        return listOf(
            ChatMessage(
                id = "msg-1",
                workspaceId = "cs-301",
                sender = MessageSender.USER,
                content = "Can you explain how AVL tree self-balancing works when doing a left-right (LR) double rotation? Also reference my lecture slides.",
                timestamp = "09:42 AM"
            ),
            ChatMessage(
                id = "msg-2",
                workspaceId = "cs-301",
                sender = MessageSender.ASSISTANT,
                content = Needle3Runtime.buildAcademicAnswer("AVL tree left-right rotation", 14),
                timestamp = "09:42 AM",
                latencyFormatted = "1.4s",
                tokensPerSec = 48,
                modelName = "Needle3",
                isZeroEgress = true,
                citations = citations,
                codeSnippet = "// Double Rotation: Left-Right (LR)\nNode rotateLeftRight(Node node) {\n    node.left = rotateLeft(node.left);\n    return rotateRight(node);\n}",
                codeLanguage = "avl_tree.java",
                formula = "BF(v) = height(v.left) - height(v.right) in {-1, 0, +1}"
            )
        )
    }

    private fun createSampleQuizQuestions(): List<QuizQuestion> {
        return listOf(
            QuizQuestion(
                id = "q-1",
                questionIndex = 1,
                questionType = "Code & Invariant Trace",
                questionText = "When performing a Left-Right (LR) double rotation on an unbalanced AVL node with Balance Factor = +2, what is the exact order of single rotations performed, and which node becomes the new local subtree root?",
                options = listOf(
                    "Option A: Single Left rotation on Node B, followed by Right rotation on Node A; Node C becomes root.",
                    "Option B: Single Right rotation on Node A, followed by Left rotation on Node B; Node B becomes root.",
                    "Option C: Double Left rotation on Node A; Node C becomes root.",
                    "Option D: Single Right rotation on Node B, followed by Left rotation on Node C; Node A remains root."
                ),
                correctIndex = 0,
                explanation = "A Left-Right (LR) imbalance occurs when the left child is right-heavy. We first perform a left rotation on child B to align the imbalance into a straight left-left line (LL), then perform a right rotation on parent A. Node C is promoted to the local root.",
                slideCitation = "CS2040 Slide 14 Grounding",
                subtreeDiagram = "Node A (BF = +2) —[L]—> Node B (BF = -1) —[R]—> Node C",
                userSelectedIndex = 0
            ),
            QuizQuestion(
                id = "q-2",
                questionIndex = 2,
                questionType = "Deletion Complexity",
                questionText = "What is the maximum number of structural rotations required to restore the AVL balance property following the deletion of a single leaf node?",
                options = listOf(
                    "Option A: Exactly zero rotations because deletion never increases tree height.",
                    "Option B: At most one single or double rotation (O(1)).",
                    "Option C: Up to O(log n) rotations cascading along ancestors to the root.",
                    "Option D: Up to O(n) rotations across all sibling subtrees."
                ),
                correctIndex = 2,
                explanation = "Unlike tree insertions which restore global invariants with at most one rotation (O(1)), deletion can decrease subtree height, causing balance factor violations that cascade iteratively up to the root.",
                slideCitation = "CS2040 Slide 18 Grounding",
                userSelectedIndex = 1 // selected B initially (wrong) in screenshot 6!
            )
        )
    }

    private fun WorkspaceEntity.toDomain() = Workspace(
        id = id,
        title = title,
        courseCode = courseCode,
        category = category,
        description = description,
        documentCount = documentCount,
        flashcardCount = flashcardCount,
        quizCount = quizCount,
        lastStudied = lastStudied,
        isPinned = isPinned,
        sourceCoordinates = listOf("CS2040_W04_Slides.pdf", "CLRS_Ch4_Trees.pdf", "Exam_Review_Notes.md")
    )

    private fun DocumentEntity.toDomain() = Document(
        id = id,
        workspaceId = workspaceId,
        title = title,
        fileName = fileName,
        fileType = fileType,
        pageCount = pageCount,
        fileSizeFormatted = fileSizeFormatted,
        isVectorized = isVectorized,
        createdAt = createdAt,
        excerpt = excerpt
    )
}
