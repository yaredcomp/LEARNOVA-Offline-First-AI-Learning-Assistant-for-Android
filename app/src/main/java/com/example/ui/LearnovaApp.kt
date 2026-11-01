package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ai.ModelManager
import com.example.data.repository.LearnovaRepository
import com.example.export.DocumentExporter
import com.example.model.ExportOptions
import com.example.model.InferenceMode
import com.example.ui.components.LearnovaBottomBar
import com.example.ui.components.LearnovaTopBar
import com.example.ui.components.NavDestination
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.DocumentViewerScreen
import com.example.ui.screens.ExportCenterScreen
import com.example.ui.screens.ExportPipelineScreen
import com.example.ui.screens.ExportSuccessScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ModelManagerScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.QuizAttemptScreen
import com.example.ui.screens.QuizCreationScreen
import com.example.ui.screens.QuizResultScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StudyModeScreen
import com.example.ui.screens.WorkspacesScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class ScreenState {
    ONBOARDING,
    MAIN,
    DOCUMENT_VIEWER,
    QUIZ_CREATION,
    QUIZ_ATTEMPT,
    QUIZ_RESULT,
    MODEL_MANAGER,
    EXPORT_CENTER,
    EXPORT_PIPELINE,
    EXPORT_SUCCESS,
    SETTINGS
}

@Composable
fun LearnovaApp(
    repository: LearnovaRepository,
    modelManager: ModelManager
) {
    val context = LocalContext.current
    var screenState by remember { mutableStateOf(ScreenState.MAIN) }
    var currentNavDestination by remember { mutableStateOf(NavDestination.HOME) }
    var isAmharic by remember { mutableStateOf(false) }

    val workspaces by repository.workspaces.collectAsState(initial = emptyList())
    val inferenceMode by repository.inferenceMode.collectAsState()
    val chatMessages by repository.chatMessages.collectAsState()
    val activeQuiz by repository.activeQuiz.collectAsState()
    val lastAttempt by repository.lastQuizAttempt.collectAsState()
    val cloudConfig by repository.cloudProviderConfig.collectAsState()

    val models by modelManager.models.collectAsState()
    val downloadProgress by modelManager.activeDownloadProgress.collectAsState()

    var activeExportOptions by remember {
        mutableStateOf(ExportOptions(targetArtifactTitle = "Comprehensive Study Guide", format = "PDF"))
    }

    // Handle Android system back button
    if (screenState != ScreenState.MAIN && screenState != ScreenState.ONBOARDING) {
        BackHandler {
            screenState = ScreenState.MAIN
        }
    } else if (screenState == ScreenState.MAIN && currentNavDestination != NavDestination.HOME) {
        BackHandler {
            currentNavDestination = NavDestination.HOME
        }
    }

    when (screenState) {
        ScreenState.ONBOARDING -> {
            OnboardingScreen(
                onGetStarted = { screenState = ScreenState.MAIN },
                onConfigureProviders = { screenState = ScreenState.SETTINGS }
            )
        }

        ScreenState.DOCUMENT_VIEWER -> {
            DocumentViewerScreen(
                slideNumber = 14,
                onBack = { screenState = ScreenState.MAIN },
                onExplainWithAI = {
                    currentNavDestination = NavDestination.CHAT
                    screenState = ScreenState.MAIN
                },
                onCreateQuiz = {
                    screenState = ScreenState.QUIZ_CREATION
                }
            )
        }

        ScreenState.QUIZ_CREATION -> {
            QuizCreationScreen(
                onBack = { screenState = ScreenState.MAIN },
                onGenerateQuiz = { count, difficulty ->
                    repository.generateQuiz("AVL Trees & Rotations", count, difficulty)
                    screenState = ScreenState.QUIZ_ATTEMPT
                }
            )
        }

        ScreenState.QUIZ_ATTEMPT -> {
            activeQuiz?.let { quiz ->
                QuizAttemptScreen(
                    quiz = quiz,
                    onBack = { screenState = ScreenState.MAIN },
                    onSubmitQuiz = { answers ->
                        repository.submitQuizAnswers(quiz.id, answers)
                        screenState = ScreenState.QUIZ_RESULT
                    }
                )
            } ?: run {
                screenState = ScreenState.MAIN
            }
        }

        ScreenState.QUIZ_RESULT -> {
            lastAttempt?.let { attempt ->
                QuizResultScreen(
                    attempt = attempt,
                    onBack = { screenState = ScreenState.MAIN },
                    onContinueStudy = {
                        currentNavDestination = NavDestination.STUDY
                        screenState = ScreenState.MAIN
                    },
                    onExportNotes = {
                        screenState = ScreenState.EXPORT_CENTER
                    }
                )
            } ?: run {
                screenState = ScreenState.MAIN
            }
        }

        ScreenState.MODEL_MANAGER -> {
            ModelManagerScreen(
                catalog = models,
                downloadProgress = downloadProgress,
                onBack = { screenState = ScreenState.MAIN },
                onDownloadModel = { id -> modelManager.startDownload(id) },
                onPurgeWeights = { id ->
                    modelManager.purgeModelWeights(id)
                    Toast.makeText(context, "Needle3 model weights purged from storage.", Toast.LENGTH_SHORT).show()
                }
            )
        }

        ScreenState.EXPORT_CENTER -> {
            ExportCenterScreen(
                onBack = { screenState = ScreenState.MAIN },
                onStartCompilation = { options ->
                    activeExportOptions = options
                    screenState = ScreenState.EXPORT_PIPELINE
                }
            )
        }

        ScreenState.EXPORT_PIPELINE -> {
            ExportPipelineScreen(
                options = activeExportOptions,
                onCancel = { screenState = ScreenState.EXPORT_CENTER },
                onCompilationComplete = {
                    // Trigger real PDF/DOCX generation in background
                    val ws = workspaces.firstOrNull() ?: com.example.model.Workspace(
                        id = "cs-301",
                        title = "Data Structures & Algorithms",
                        courseCode = "CS-301",
                        category = "Core Module",
                        description = "AVL Trees",
                        documentCount = 8,
                        flashcardCount = 32,
                        quizCount = 14,
                        lastStudied = "Today"
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        if (activeExportOptions.format == "PDF") {
                            DocumentExporter.generateAcademicPdf(context, activeExportOptions, ws)
                        } else {
                            DocumentExporter.generateAcademicDocx(context, activeExportOptions, ws)
                        }
                    }
                    screenState = ScreenState.EXPORT_SUCCESS
                }
            )
        }

        ScreenState.EXPORT_SUCCESS -> {
            ExportSuccessScreen(
                options = activeExportOptions,
                onOpenPdfViewer = { screenState = ScreenState.DOCUMENT_VIEWER },
                onBackToWorkspace = {
                    currentNavDestination = NavDestination.WORKSPACES
                    screenState = ScreenState.MAIN
                }
            )
        }

        ScreenState.SETTINGS -> {
            SettingsScreen(
                currentMode = inferenceMode,
                onSelectMode = { repository.setInferenceMode(it) },
                cloudConfig = cloudConfig,
                onSaveCloudConfig = { repository.updateCloudConfig(it) },
                onBack = { screenState = ScreenState.MAIN }
            )
        }

        ScreenState.MAIN -> {
            Scaffold(
                topBar = {
                    LearnovaTopBar(
                        isAmharic = isAmharic,
                        onToggleLanguage = { isAmharic = !isAmharic },
                        onOpenSettings = { screenState = ScreenState.SETTINGS },
                        onOpenModelManager = { screenState = ScreenState.MODEL_MANAGER }
                    )
                },
                bottomBar = {
                    LearnovaBottomBar(
                        currentDestination = currentNavDestination,
                        isAmharic = isAmharic,
                        onNavigate = { dest -> currentNavDestination = dest }
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    when (currentNavDestination) {
                        NavDestination.HOME -> {
                            HomeScreen(
                                workspaces = workspaces,
                                inferenceMode = inferenceMode,
                                onSelectInferenceMode = { repository.setInferenceMode(it) },
                                onRunQuery = { text ->
                                    repository.sendMessage(text)
                                    currentNavDestination = NavDestination.CHAT
                                },
                                onOpenWorkspace = { wsId ->
                                    repository.setCurrentWorkspace(wsId)
                                    currentNavDestination = NavDestination.WORKSPACES
                                },
                                onOpenModelManager = { screenState = ScreenState.MODEL_MANAGER },
                                onOpenQuizCreation = { screenState = ScreenState.QUIZ_CREATION }
                            )
                        }

                        NavDestination.WORKSPACES -> {
                            WorkspacesScreen(
                                workspaces = workspaces,
                                onOpenWorkspace = { wsId ->
                                    repository.setCurrentWorkspace(wsId)
                                    screenState = ScreenState.DOCUMENT_VIEWER
                                },
                                onAskAI = { wsId ->
                                    repository.setCurrentWorkspace(wsId)
                                    currentNavDestination = NavDestination.CHAT
                                },
                                onImportDocument = {
                                    Toast.makeText(context, "Document picker: Ready for local vectorization (PDF, PPTX, DOCX, MD)", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }

                        NavDestination.CHAT -> {
                            ChatScreen(
                                messages = chatMessages,
                                onSendMessage = { text -> repository.sendMessage(text) },
                                onJumpToSlide = { slide ->
                                    screenState = ScreenState.DOCUMENT_VIEWER
                                }
                            )
                        }

                        NavDestination.STUDY -> {
                            StudyModeScreen(
                                onDeconstruct = { concept ->
                                    repository.sendMessage("Dissect concept: $concept with step-by-step invariant proofs.")
                                    currentNavDestination = NavDestination.CHAT
                                },
                                onStartQuiz = {
                                    screenState = ScreenState.QUIZ_CREATION
                                }
                            )
                        }

                        NavDestination.LIBRARY -> {
                            ModelManagerScreen(
                                catalog = models,
                                downloadProgress = downloadProgress,
                                onBack = { currentNavDestination = NavDestination.HOME },
                                onDownloadModel = { id -> modelManager.startDownload(id) },
                                onPurgeWeights = { id ->
                                    modelManager.purgeModelWeights(id)
                                    Toast.makeText(context, "Model weights purged.", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
