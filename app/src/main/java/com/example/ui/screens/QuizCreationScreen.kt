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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SurfaceCanvas
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun QuizCreationScreen(
    onBack: () -> Unit,
    onGenerateQuiz: (Int, String) -> Unit
) {
    var strictLock by remember { mutableStateOf(true) }
    var selectedVolume by remember { mutableStateOf(10) }
    var selectedDifficulty by remember { mutableStateOf("Intermediate") }

    var mcqChecked by remember { mutableStateOf(true) }
    var trueFalseChecked by remember { mutableStateOf(true) }
    var codeTraceChecked by remember { mutableStateOf(true) }
    var proofChecked by remember { mutableStateOf(false) }

    var slideCitationsGuard by remember { mutableStateOf(true) }
    var spacedRepetitionGuard by remember { mutableStateOf(true) }
    var timedExamGuard by remember { mutableStateOf(false) }

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
                        text = "Quiz Creation — Active Recall",
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

            // Scrollable Settings Form
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Section 1: Recall Scope
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
                            Text(text = "🎯 Recall Scope", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                            Text(text = "Step 1 of 3", fontSize = 11.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Target Subject / Core Theorem", fontSize = 11.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceCanvas)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Folder, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "AVL Trees, Rebalancing Invariants & Double Rotations",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = TextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Strict Grounding Lock Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Strict Grounding Lock", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                                Text(
                                    text = "Restrict questions solely to verified lecture slides. Guarantees 0% speculative hallucinations.",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    lineHeight = 15.sp
                                )
                            }
                            Switch(
                                checked = strictLock,
                                onCheckedChange = { strictLock = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryGreen)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 2: Question Volume
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
                            Text(text = "🔢 Question Volume", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                            Text(text = "~15 min duration", fontSize = 11.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            VolumeOption(5, "Rapid checkpoint", selectedVolume == 5) { selectedVolume = 5 }
                            VolumeOption(10, "Recommended • Deep retention", selectedVolume == 10) { selectedVolume = 10 }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            VolumeOption(15, "Midterm stamina", selectedVolume == 15) { selectedVolume = 15 }
                            VolumeOption(20, "Full chapter mastery", selectedVolume == 20) { selectedVolume = 20 }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 3: Difficulty Calibration
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
                            Text(text = "⚖ Difficulty Calibration", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                            Text(text = "Bloom Level: Analyze", fontSize = 11.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Foundational", "Intermediate", "Proofs").forEach { diff ->
                                val isSelected = diff == selectedDifficulty
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) PrimaryGreen else SurfaceCanvas)
                                        .clickable { selectedDifficulty = diff }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = diff,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else TextPrimary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ℹ Standard university exam standard: requires computing node balance factors and single vs. double rotation triggers.",
                            fontSize = 11.sp,
                            color = TextSecondary,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 4: Question Paradigms
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
                            Text(text = "🧩 Question Paradigms", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                            Text(text = "3 Selected", fontSize = 11.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        ParadigmCheckItem("Multiple Choice", "4 Options", "Distractor analysis rooted in standard misconceptions", mcqChecked) { mcqChecked = it }
                        ParadigmCheckItem("Conceptual True / False", "Core Principles", "Tests boundary conditions and strict definition validity", trueFalseChecked) { trueFalseChecked = it }
                        ParadigmCheckItem("Code & Invariant Trace", "Algorithmic", "Identify rotation output and post-insertion pointer updates", codeTraceChecked) { codeTraceChecked = it }
                        ParadigmCheckItem("Short Answer Proof", "Freeform", "Derive log(n) height bound invariant from Fibonacci lemma", proofChecked) { proofChecked = it }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pedagogical Guardrails
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "🛡 Pedagogical Guardrails", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                        Spacer(modifier = Modifier.height(10.dp))

                        GuardrailSwitch("Detailed Explanations & Slide Citations", "Shows rationale linking directly back to page numbers", slideCitationsGuard) { slideCitationsGuard = it }
                        Spacer(modifier = Modifier.height(8.dp))
                        GuardrailSwitch("Spaced Repetition Auto-Tagging", "Syncs incorrect answer concepts to daily Anki review queue", spacedRepetitionGuard) { spacedRepetitionGuard = it }
                        Spacer(modifier = Modifier.height(8.dp))
                        GuardrailSwitch("Timed Exam Simulation", "Paces strictly at 90 seconds per question with exam countdown", timedExamGuard) { timedExamGuard = it }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Execution Banner: Needle3 On-Device LLM
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Outlined.Memory, contentDescription = null, tint = PrimaryGreen)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Needle3 On-Device LLM", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(PrimaryContainer)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = "● Ready", fontSize = 10.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(text = "Zero cloud egress • Complete academic privacy", fontSize = 11.sp, color = TextSecondary)
                            Text(text = "⚡ Est. Latency: ~8 seconds  •  Snapdragon NPU", fontSize = 10.sp, color = PrimaryGreen, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Generate Button
                Button(
                    onClick = { onGenerateQuiz(selectedVolume, selectedDifficulty) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("generate_quiz_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Generate Quiz ($selectedVolume Questions)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Cites specific lecture slides and proof invariants directly.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.VolumeOption(
    count: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) PrimaryContainer.copy(alpha = 0.5f) else SurfaceCanvas)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$count Questions", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                if (isSelected) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(16.dp))
                }
            }
            Text(text = label, fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun ParadigmCheckItem(
    title: String,
    tag: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = PrimaryGreen)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = tag, fontSize = 10.sp, color = TextSecondary)
            }
            Text(text = description, fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
        }
    }
}

@Composable
private fun GuardrailSwitch(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TextPrimary)
            Text(text = description, fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryGreen)
        )
    }
}
