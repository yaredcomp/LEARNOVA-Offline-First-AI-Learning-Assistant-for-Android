package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ModelCatalogItem
import com.example.model.ModelStatus
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorDestructive
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.StatusDownloading
import com.example.ui.theme.StatusInstalled
import com.example.ui.theme.StatusReady
import com.example.ui.theme.StatusUnsupported
import com.example.ui.theme.SurfaceCanvas
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ModelManagerScreen(
    catalog: List<ModelCatalogItem>,
    downloadProgress: Int,
    onBack: () -> Unit,
    onDownloadModel: (String) -> Unit,
    onPurgeWeights: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf("All Models") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SurfaceCanvas
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Bar
            Surface(color = SurfaceCanvas, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "Model Manager",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(PrimaryContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = "● Edge", fontSize = 11.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Storage Partition Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Storage, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Storage Partition", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                            }
                            Text(text = "eMMC/UFS 3.1", fontSize = 10.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Segmented bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceCanvas)
                        ) {
                            Box(modifier = Modifier.weight(0.12f).fillMaxSize().background(PrimaryGreen))
                            Box(modifier = Modifier.weight(0.35f).fillMaxSize().background(Color.LightGray))
                            Box(modifier = Modifier.weight(0.53f).fillMaxSize().background(PrimaryContainer))
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "● Local AI: 2.1 GB", fontSize = 10.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                            Text(text = "● Other Apps: 18.3 GB", fontSize = 10.sp, color = TextSecondary)
                            Text(text = "● 42.8 GB free", fontSize = 10.sp, color = TextSecondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Quantized Runtime Sync (Verification Banner)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryContainer.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "Quantized Runtime Sync", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                                    Text(text = "Needle3-Int4-GGUF Checksum Verification", fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF0284C7).copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = "Background", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0284C7))
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(imageVector = Icons.Default.Pause, contentDescription = "Pause", tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "$downloadProgress% — 1.15 GB / 1.80 GB (SHA-256 Validating)", fontSize = 10.sp, color = TextSecondary)
                            Text(text = "14.2 MB/s", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        LinearProgressIndicator(
                            progress = { downloadProgress / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = PrimaryGreen,
                            trackColor = SurfaceCard
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "🛡 Guaranteed 100% offline functionality once unpacked into system cache.",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Primary Active Engine Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "✪", color = PrimaryGreen, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Primary Active Engine",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Text(text = "STANDARD DEFAULT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Needle3 Primary Engine Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Needle3", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(999.dp))
                                            .background(PrimaryContainer)
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(text = "● Installed & Ready", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                                    }
                                }
                                Text(text = "Learnova Lightweight Academic AI", fontSize = 11.sp, color = TextSecondary)
                            }
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF80DA93).copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Memory, contentDescription = null, tint = PrimaryGreen)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Publisher & Origin link
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceCanvas)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Publisher & Origin", fontSize = 10.sp, color = TextSecondary)
                                Text(text = "Cactus Compute • Hugging Face", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceCard)
                                    .clickable {
                                        Toast.makeText(context, "Canonical Repo: huggingface.co/Cactus-Compute/needle3", Toast.LENGTH_LONG).show()
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Repo", fontSize = 11.sp, color = TextPrimary)
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(12.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceCanvas)
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text(text = "Weight Size", fontSize = 10.sp, color = TextSecondary)
                                    Text(text = "1.8 GB", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text(text = "Q4_K_M Quantized", fontSize = 9.sp, color = TextSecondary)
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceCanvas)
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text(text = "RAM Footprint", fontSize = 10.sp, color = TextSecondary)
                                    Text(text = "~2.4 GB", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text(text = "Active execution peak", fontSize = 9.sp, color = TextSecondary)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryContainer.copy(alpha = 0.5f))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Compatible with Snapdragon 8 Gen / Dimensity / Arm64", fontSize = 11.sp, color = TextPrimary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "OPTIMIZED CAPABILITIES", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("Lightweight", "Offline Chat", "Document Q&A", "Text Extraction").forEach { cap ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .background(SurfaceCanvas)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(text = cap, fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    Toast.makeText(context, "Needle3 benchmark: 48 tokens/sec • 1.4s TTFT", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                            ) {
                                Icon(imageVector = Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Benchmark", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedButton(
                                onClick = {},
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(text = "Details", fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Purge weights button
                        Button(
                            onClick = { onPurgeWeights("needle3-default") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ErrorContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = ErrorDestructive, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Purge Stored Weights (1.8 GB)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ErrorDestructive)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Available Model Catalog Header & Filters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Available Model Catalog", fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif, color = TextPrimary)
                    Text(text = "3 Additional Found", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf("All Models", "Downloaded (2)", "Document Q&A", "Lightweight")) { tab ->
                        val isSelected = tab == selectedTab
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(if (isSelected) PrimaryGreen else SurfaceCard)
                                .clickable { selectedTab = tab }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tab,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Catalog Models
                catalog.filter { it.id != "needle3-default" }.forEach { model ->
                    ModelCatalogCard(
                        model = model,
                        onDownload = { onDownloadModel(model.id) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Academic Integrity Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryContainer.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = "🎓 Offline Academic Integrity Notice", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrimaryGreen)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "All Needle neural weights run strictly via internal Arm CPU/NPU hardware instructions. Prompt embeddings and user notes are never transmitted off this terminal.",
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            color = TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun ModelCatalogCard(
    model: ModelCatalogItem,
    onDownload: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = model.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceCanvas)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = model.tag, fontSize = 10.sp, color = TextSecondary)
                    }
                }
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceCanvas),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Memory, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = model.description, fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))

            when (model.status) {
                ModelStatus.INSTALLED_READY -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Installed & Ready", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }
                        OutlinedButton(onClick = {}, shape = RoundedCornerShape(8.dp)) {
                            Text(text = "Vector Index", fontSize = 11.sp)
                        }
                    }
                }
                ModelStatus.AVAILABLE_TO_DOWNLOAD -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Requires ≥ ${model.requiresMinRamGb} GB RAM", fontSize = 10.sp, color = TextSecondary)
                        Button(
                            onClick = onDownload,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Download (${model.weightSizeFormatted})", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                ModelStatus.DOWNLOADING -> {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Downloading...", fontSize = 11.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                            Text(text = "${model.downloadProgressPercent}%", fontSize = 11.sp, color = PrimaryGreen)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { model.downloadProgressPercent / 100f },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                            color = PrimaryGreen
                        )
                    }
                }
                ModelStatus.INCOMPATIBLE -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Block, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Not Compatible (Requires ≥ 8GB RAM)", fontSize = 10.sp, color = TextSecondary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceCanvas)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "Incompatible", fontSize = 10.sp, color = TextSecondary)
                        }
                    }
                }
            }
        }
    }
}
