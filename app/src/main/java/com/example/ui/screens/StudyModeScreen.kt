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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.ui.theme.WarningNotice

@Composable
fun StudyModeScreen(
    onDeconstruct: (String) -> Unit,
    onStartQuiz: () -> Unit
) {
    var queryText by remember { mutableStateOf("") }
    var selectedRigor by remember { mutableStateOf("Formal Proof") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SurfaceCanvas
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "DEEP SYNTHESIS MODE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "What do you want to understand?",
                fontSize = 24.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Query Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    OutlinedTextField(
                        value = queryText,
                        onValueChange = { queryText = it },
                        placeholder = {
                            Text(
                                text = "Enter a concept, theorem, or question to dissect...",
                                fontSize = 13.sp,
                                color = TextSecondary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .testTag("study_query_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                            focusedContainerColor = SurfaceCanvas,
                            unfocusedContainerColor = SurfaceCanvas
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "Camera", tint = TextSecondary) }
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.Mic, contentDescription = "Mic", tint = TextSecondary) }
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.AttachFile, contentDescription = "Attach", tint = TextSecondary) }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                val text = if (queryText.isNotBlank()) queryText else "AVL Tree Left-Right Double Rotation"
                                onDeconstruct(text)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                        ) {
                            Text(
                                text = "Deconstruct",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Session Parameters Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚙ Session Parameters",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TextPrimary
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(PrimaryContainer)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Zero Egress", fontSize = 10.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceCanvas)
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "📑", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Anchored Source Context", fontSize = 10.sp, color = TextSecondary)
                                Text(text = "Binary Search Trees & AVL Rotations (Slide 14)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Explanatory Rigor", fontSize = 11.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "Intuitive" to "Analogy-first",
                            "Formal Proof" to "Math & Invariants",
                            "Exam Cram" to "High-yield chips"
                        ).forEach { (rigor, sub) ->
                            val isSelected = rigor == selectedRigor
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PrimaryGreen else SurfaceCanvas)
                                    .clickable { selectedRigor = rigor }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = rigor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else TextPrimary
                                    )
                                    Text(
                                        text = sub,
                                        fontSize = 9.sp,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Dissection Modes Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dissection Modes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = TextPrimary
                )
                Text(
                    text = "8 Local Directives",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 8 Dissection Modes
            DissectionModeCard(
                icon = Icons.Default.Lightbulb,
                title = "Explain a Concept",
                description = "Deep breakdown with physical analogies & memory hooks."
            ) { onDeconstruct("Explain AVL Trees with analogies") }

            Spacer(modifier = Modifier.height(8.dp))

            DissectionModeCard(
                icon = Icons.Default.FormatListNumbered,
                title = "Step-by-Step",
                description = "Trace proof steps, execution state & recursion trees."
            ) { onDeconstruct("Trace AVL LR rotation step by step") }

            Spacer(modifier = Modifier.height(8.dp))

            DissectionModeCard(
                icon = Icons.Default.Code,
                title = "Concrete Example",
                description = "Minimal runnable code cases & dry-run walkthroughs."
            ) { onDeconstruct("Provide runnable Java example of AVL rotation") }

            Spacer(modifier = Modifier.height(8.dp))

            DissectionModeCard(
                icon = Icons.Default.Description,
                title = "Summarize Source",
                description = "Distill theorems, core lemmas & asymptotic complexity."
            ) { onDeconstruct("Summarize Lemma 4.1 and Theorem 13.2") }

            Spacer(modifier = Modifier.height(8.dp))

            DissectionModeCard(
                icon = Icons.Default.CompareArrows,
                title = "Compare Two",
                description = "Contrasting matrix: AVL vs Red-Black Trees."
            ) { onDeconstruct("Compare AVL vs Red-Black tree invariants") }

            Spacer(modifier = Modifier.height(8.dp))

            DissectionModeCard(
                icon = Icons.Default.Warning,
                title = "Exam Pitfalls",
                description = "Edge-case traps, pointer de-referencing & proof holes."
            ) { onDeconstruct("What are common exam traps for AVL trees?") }

            Spacer(modifier = Modifier.height(8.dp))

            DissectionModeCard(
                icon = Icons.Default.Quiz,
                title = "Practice Quiz",
                badge = "Active Recall",
                description = "Adaptive MCQ & free-response prompts.",
                onClick = onStartQuiz
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Active Recall Queue
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🌀 Active Recall Queue",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = TextPrimary
                )
                Text(text = "View All (18)", fontSize = 12.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "84%", fontWeight = FontWeight.Bold, color = PrimaryGreen, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Self-Balancing Trees", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFFFE8D8))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = "8 due", fontSize = 10.sp, color = Color(0xFFD97706), fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(text = "CS-301 • Right-Left (RL) Double Rotations", fontSize = 11.sp, color = TextSecondary)
                    }
                    Button(
                        onClick = onStartQuiz,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text(text = "Drill Now", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun DissectionModeCard(
    icon: ImageVector,
    title: String,
    description: String,
    badge: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                    if (badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(PrimaryContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = badge, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }
                    }
                }
                Text(text = description, fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
            }
        }
    }
}
