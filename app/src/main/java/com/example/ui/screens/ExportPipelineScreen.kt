package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExportOptions
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SurfaceCanvas
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun ExportPipelineScreen(
    options: ExportOptions,
    onCancel: () -> Unit,
    onCompilationComplete: () -> Unit
) {
    var progress by remember { mutableFloatStateOf(0.2f) }

    LaunchedEffect(Unit) {
        delay(600)
        progress = 0.5f
        delay(800)
        progress = 0.76f
        delay(900)
        progress = 1.0f
        delay(400)
        onCompilationComplete()
    }

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
                    IconButton(onClick = onCancel) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "Export Center — Academic Artifact",
                        fontSize = 16.sp,
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

            // Scrollable Pipeline View
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Header Metadata
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "ACADEMIC ARTIFACT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen, letterSpacing = 1.sp)
                    Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(SurfaceCanvas).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text(text = "Stage 3 of 5", fontSize = 10.sp, color = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = options.targetFileName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = "PDF/A • Est. 2.4 MB • 6 Pages • 10 Grounded Citations", fontSize = 11.sp, color = TextSecondary)

                Spacer(modifier = Modifier.height(14.dp))

                // Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${(progress * 100).toInt()}% completed", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "● Active Vectorize", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = PrimaryGreen,
                    trackColor = PrimaryContainer
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Compiling Page 4 of 6: LaTeX Proofs & Vector Diagrams", fontSize = 11.sp, color = TextSecondary)
                    Text(text = "~1.1s left", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Live Page Buffer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Live Page Buffer", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "4 of 6 rendered", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { PageBufferThumb("P1: Cover", "Done • 120KB", isDone = true) }
                    item { PageBufferThumb("P2: BST Invar.", "Done • 380KB", isDone = true) }
                    item { PageBufferThumb("P3: Single Rot.", "Done • 410KB", isDone = true) }
                    item { PageBufferThumb("P4: Double Rot.", "Rasterizing...", isDone = false) }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Compilation Pipeline Checklist
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Compilation Pipeline", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "⚙ Single-Pass Assembler", fontSize = 11.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        PipelineStepItem(
                            title = "Extracting Grounded Citations",
                            detail = "10 slide references verified against local SQLite vault (Slide 14–18). Done.",
                            duration = "0.4s",
                            isComplete = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PipelineStepItem(
                            title = "Rendering LaTeX Equation Canvas",
                            detail = "Vectorizing AVL height formulas & balance factor mathematical proofs. Done.",
                            duration = "0.8s",
                            isComplete = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PipelineStepItem(
                            title = "Synthesizing Vector Diagrams (Page 4)",
                            detail = "Rendering before/after rotation graph trees & balance factor nodes at 300 DPI.",
                            duration = "1.2s...",
                            isComplete = false,
                            inProgress = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PipelineStepItem(
                            title = "Injecting University Cover & Metadata",
                            detail = "Formatting faculty header, term code & timestamp stamp.",
                            duration = "Queued",
                            isComplete = false
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Local Hardware Telemetry
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "📊 Local Hardware Telemetry", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "Offline Core", fontSize = 10.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TelemetryCard("⚡ NPU / CPU Load", "38%", "Snapdragon NPU active")
                    TelemetryCard("💾 RAM Allocation", "42 MB", "WASM sandbox limit")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TelemetryCard("⏱ Elapsed Time", "2.4s", "Compiling on thread #3")
                    TelemetryCard("📡 Socket Activity", "0.0 KB/s", "Fully Air-gapped")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onCompilationComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("keep_compiling_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                ) {
                    Text(text = "Keep Compiling in Background", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "✕ Cancel Export",
                    fontSize = 12.sp,
                    color = Color(0xFFBA1A1A),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onCancel() }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PageBufferThumb(title: String, status: String, isDone: Boolean) {
    Card(
        modifier = Modifier.width(100.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceCanvas),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
                } else {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = status, fontSize = 9.sp, color = if (isDone) PrimaryGreen else TextSecondary)
        }
    }
}

@Composable
private fun PipelineStepItem(
    title: String,
    detail: String,
    duration: String,
    isComplete: Boolean,
    inProgress: Boolean = false
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        if (isComplete) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
        } else if (inProgress) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
        } else {
            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = duration, fontSize = 10.sp, color = if (isComplete || inProgress) PrimaryGreen else TextSecondary)
            }
            Text(text = detail, fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.TelemetryCard(
    label: String,
    value: String,
    sub: String
) {
    Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = label, fontSize = 10.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = sub, fontSize = 9.sp, color = PrimaryGreen)
        }
    }
}
