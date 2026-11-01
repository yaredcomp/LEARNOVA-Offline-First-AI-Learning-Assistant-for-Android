package com.example.ai

import com.example.model.ModelCatalogItem
import com.example.model.ModelStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ModelManager {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var downloadJob: Job? = null

    private val _models = MutableStateFlow<List<ModelCatalogItem>>(defaultCatalog())
    val models: StateFlow<List<ModelCatalogItem>> = _models.asStateFlow()

    private val _activeDownloadProgress = MutableStateFlow(64)
    val activeDownloadProgress: StateFlow<Int> = _activeDownloadProgress.asStateFlow()

    private val _isDownloading = MutableStateFlow(false)
    val isDownloading: StateFlow<Boolean> = _isDownloading.asStateFlow()

    fun startDownload(modelId: String) {
        val currentList = _models.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == modelId }
        if (index == -1) return

        _isDownloading.value = true
        currentList[index] = currentList[index].copy(
            status = ModelStatus.DOWNLOADING,
            downloadProgressPercent = 10,
            downloadSpeedFormatted = "14.2 MB/s"
        )
        _models.value = currentList

        downloadJob?.cancel()
        downloadJob = scope.launch {
            for (p in 10..100 step 15) {
                delay(300)
                _activeDownloadProgress.value = p
                val updated = _models.value.toMutableList()
                val idx = updated.indexOfFirst { it.id == modelId }
                if (idx != -1) {
                    updated[idx] = updated[idx].copy(
                        downloadProgressPercent = p,
                        downloadSpeedFormatted = if (p < 100) "14.2 MB/s" else "Completed"
                    )
                    _models.value = updated
                }
            }
            // Mark installed
            val final = _models.value.toMutableList()
            val idx = final.indexOfFirst { it.id == modelId }
            if (idx != -1) {
                final[idx] = final[idx].copy(
                    status = ModelStatus.INSTALLED_READY,
                    downloadProgressPercent = 100
                )
                _models.value = final
            }
            _isDownloading.value = false
        }
    }

    fun purgeModelWeights(modelId: String) {
        val currentList = _models.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == modelId }
        if (index != -1) {
            currentList[index] = currentList[index].copy(
                status = ModelStatus.AVAILABLE_TO_DOWNLOAD,
                downloadProgressPercent = 0
            )
            _models.value = currentList
        }
    }

    companion object {
        fun defaultCatalog(): List<ModelCatalogItem> = listOf(
            ModelCatalogItem(
                id = "needle3-default",
                name = "Needle3",
                publisher = "Cactus Compute • Hugging Face",
                repo = "https://huggingface.co/Cactus-Compute/needle3",
                tag = "INT4 Quant",
                weightSizeFormatted = "1.8 GB",
                ramFootprintFormatted = "~2.4 GB",
                status = ModelStatus.INSTALLED_READY,
                downloadProgressPercent = 100,
                description = "Learnova Lightweight Academic AI. Optimized for lightweight on-device Q&A, formula extraction, and function calling.",
                compatibleChips = "Compatible with Snapdragon 8 Gen / Dimensity / Arm64",
                capabilities = listOf("Lightweight", "Offline Chat", "Document Q&A", "Text Extraction", "Function Calling"),
                requiresMinRamGb = 2.0f
            ),
            ModelCatalogItem(
                id = "needle3-mini",
                name = "Needle3-Mini",
                publisher = "Cactus Compute",
                repo = "https://huggingface.co/Cactus-Compute/needle3",
                tag = "0.8 GB",
                weightSizeFormatted = "820 MB",
                ramFootprintFormatted = "~1.2 GB",
                status = ModelStatus.AVAILABLE_TO_DOWNLOAD,
                downloadProgressPercent = 0,
                description = "Ultra-lightweight model tuned for low-memory Android phones and rapid conceptual review.",
                compatibleChips = "Compatible with all 64-bit and 32-bit Arm devices",
                capabilities = listOf("Lightweight", "Active Recall", "Flashcard Q&A"),
                requiresMinRamGb = 1.2f
            ),
            ModelCatalogItem(
                id = "bge-micro",
                name = "BGE-Micro-Embeddings",
                publisher = "BAAI / Hugging Face",
                repo = "https://huggingface.co/BAAI/bge-micro-v2",
                tag = "140 MB",
                weightSizeFormatted = "140 MB",
                ramFootprintFormatted = "~210 MB",
                status = ModelStatus.INSTALLED_READY,
                downloadProgressPercent = 100,
                description = "On-device vector embeddings for offline PDF search, slide coordination, and sub-millisecond semantic retrieval.",
                compatibleChips = "Hardware accelerated via WASM / Arm Neon",
                capabilities = listOf("Semantic Retrieval", "Slide Grounding", "Cosine Similarity"),
                requiresMinRamGb = 0.5f
            ),
            ModelCatalogItem(
                id = "needle3-vision",
                name = "Needle3-Vision",
                publisher = "Cactus Compute",
                repo = "https://huggingface.co/Cactus-Compute/needle3",
                tag = "3.2 GB",
                weightSizeFormatted = "3.2 GB",
                ramFootprintFormatted = "~5.8 GB",
                status = ModelStatus.INCOMPATIBLE,
                downloadProgressPercent = 0,
                description = "Diagram & formula inspection for textbook figures. Locked: Requires >= 8 GB RAM device configuration.",
                compatibleChips = "Requires Flagship NPU (Snapdragon 8 Gen 3+)",
                capabilities = listOf("Vision OCR", "Diagram Parser", "Multimodal"),
                requiresMinRamGb = 8.0f
            )
        )
    }
}
