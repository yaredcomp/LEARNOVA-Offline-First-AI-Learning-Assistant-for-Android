package com.example.model

enum class InferenceMode(val label: String, val badge: String, val description: String) {
    LOCAL_ONLY("Local Only", "Zero Data Egress", "100% on-device inference. No prompt, document, or note leaves your hardware."),
    SMART_ROUTING("Smart Routing", "Hybrid", "Executes extraction and RAG locally; routes complex long proofs to cloud when requested."),
    CLOUD_AI("Cloud AI", "Online", "Uses configured cloud provider (Gemini / OpenRouter) with maximum reasoning depth.")
}

data class Workspace(
    val id: String,
    val title: String,
    val courseCode: String,
    val category: String,
    val description: String,
    val documentCount: Int,
    val flashcardCount: Int,
    val quizCount: Int,
    val lastStudied: String,
    val lastStudiedPage: String = "",
    val isPinned: Boolean = true,
    val sourceCoordinates: List<String> = emptyList()
)

data class Document(
    val id: String,
    val workspaceId: String,
    val title: String,
    val fileName: String,
    val fileType: String,
    val pageCount: Int,
    val fileSizeFormatted: String,
    val isVectorized: Boolean,
    val createdAt: String,
    val excerpt: String = ""
)

data class DocumentChunk(
    val id: String,
    val documentId: String,
    val workspaceId: String,
    val documentTitle: String,
    val pageNumber: Int,
    val slideNumber: Int,
    val heading: String,
    val text: String,
    val tokenCount: Int,
    val confidence: Float = 0.984f
)

data class Citation(
    val id: String,
    val documentTitle: String,
    val slideNumber: Int,
    val pageNumber: Int,
    val confidencePercent: Float,
    val excerpt: String,
    val professorQuote: String = ""
)

data class ChatMessage(
    val id: String,
    val workspaceId: String,
    val sender: MessageSender,
    val content: String,
    val timestamp: String,
    val latencyFormatted: String = "1.4s",
    val tokensPerSec: Int = 48,
    val modelName: String = "Needle3",
    val isZeroEgress: Boolean = true,
    val citations: List<Citation> = emptyList(),
    val codeSnippet: String? = null,
    val codeLanguage: String? = null,
    val formula: String? = null
)

enum class MessageSender {
    USER, ASSISTANT, SYSTEM
}

data class Quiz(
    val id: String,
    val workspaceId: String,
    val title: String,
    val topic: String,
    val difficulty: String,
    val questionCount: Int,
    val questions: List<QuizQuestion>,
    val createdAt: String,
    val lastScorePercent: Int? = null,
    val durationFormatted: String = "14m 20s"
)

data class QuizQuestion(
    val id: String,
    val questionIndex: Int,
    val questionType: String, // e.g. "Code & Invariant Trace", "Multiple Choice", "Conceptual True / False"
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val slideCitation: String,
    val subtreeDiagram: String? = null,
    val userSelectedIndex: Int? = null
)

data class QuizAttempt(
    val quizId: String,
    val score: Int,
    val total: Int,
    val timeSpentFormatted: String,
    val pacingFormatted: String,
    val citationsValidPercent: Int,
    val answers: Map<Int, Int> // questionIndex -> selectedIndex
)

enum class ModelStatus {
    INSTALLED_READY,
    DOWNLOADING,
    AVAILABLE_TO_DOWNLOAD,
    INCOMPATIBLE
}

data class ModelCatalogItem(
    val id: String,
    val name: String,
    val publisher: String,
    val repo: String,
    val tag: String,
    val weightSizeFormatted: String,
    val ramFootprintFormatted: String,
    val status: ModelStatus,
    val downloadProgressPercent: Int = 0,
    val downloadSpeedFormatted: String = "",
    val description: String,
    val compatibleChips: String,
    val capabilities: List<String>,
    val requiresMinRamGb: Float = 1.8f
)

data class ExportOptions(
    val targetArtifactTitle: String,
    val format: String, // "PDF", "DOCX"
    val includeGroundedCitations: Boolean = true,
    val includeAnswerKey: Boolean = true,
    val includeLatexMath: Boolean = true,
    val includeUniversityCover: Boolean = true,
    val includeSpacedRepetitionTags: Boolean = false,
    val targetFileName: String = "CS301_AVL_Trees_Study_Guide_2024.pdf"
)

data class CloudProviderConfig(
    val providerName: String = "Gemini 2.5 Flash",
    val apiKey: String = "",
    val customEndpoint: String = "",
    val isConnected: Boolean = false
)
