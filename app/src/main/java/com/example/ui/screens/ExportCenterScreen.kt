package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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

@Composable
fun ExportCenterScreen(
    onBack: () -> Unit,
    onStartCompilation: (ExportOptions) -> Unit
) {
    var selectedArtifact by remember { mutableStateOf("Comprehensive Study Guide") }
    var selectedFormat by remember { mutableStateOf("PDF") }
    var citationsChecked by remember { mutableStateOf(true) }
    var answerKeyChecked by remember { mutableStateOf(true) }
    var latexMathChecked by remember { mutableStateOf(true) }
    var coverPageChecked by remember { mutableStateOf(true) }
    var spacedRepetitionChecked by remember { mutableStateOf(false) }

    var targetFileName by remember { mutableStateOf("CS301_AVL_Trees_Study_Guide_2024.pdf") }

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
                        text = "Export Center — Academic Artifacts",
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

            // Scrollable Options Form
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Verified Grounded Source banner
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryContainer.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Verified Grounded Source", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrimaryGreen)
                            Text(text = "Linked to Prof. Vance's Slide Deck (Slide 14–18) & CLRS Chapter 13", fontSize = 11.sp, color = TextPrimary)
                        }
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Select Target Artifact Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Select Target Artifact", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "1 of 4 Selected", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Target Artifact Options
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ArtifactSelectableCard(
                        icon = Icons.Default.Description,
                        title = "Comprehensive Study Guide",
                        description = "Synthesis notes, formal AVL balance proofs, and Big-O performance invariants.",
                        badge = "Notes + Summary + Formulas",
                        isSelected = selectedArtifact == "Comprehensive Study Guide",
                        onClick = {
                            selectedArtifact = "Comprehensive Study Guide"
                            targetFileName = "CS301_AVL_Trees_Study_Guide_2024.pdf"
                        }
                    )
                    ArtifactSelectableCard(
                        icon = Icons.Default.Quiz,
                        title = "Active Recall Quiz Deck",
                        description = "10 multiple choice & code invariant trace prompts with rationales.",
                        badge = "10 Qs + Rationales",
                        isSelected = selectedArtifact == "Active Recall Quiz Deck",
                        onClick = {
                            selectedArtifact = "Active Recall Quiz Deck"
                            targetFileName = "CS301_Tree_Rebalancing_Quiz_Deck.pdf"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Export Format Header
                Text(text = "Export Format", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(modifier = Modifier.height(8.dp))

                FormatOptionCard(
                    title = "PDF Document (.pdf)",
                    size = "~2.4 MB",
                    description = "Publication-ready formatted document with vector diagrams, LaTeX formulas, and hyperlinked page citations.",
                    tag = "Recommended for Tablets & Print",
                    isSelected = selectedFormat == "PDF",
                    onClick = {
                        selectedFormat = "PDF"
                        if (targetFileName.endsWith(".docx")) targetFileName = targetFileName.replace(".docx", ".pdf")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormatOptionCard(
                    title = "Microsoft Word (.docx)",
                    size = "~1.1 MB",
                    description = "Editable typography with native heading hierarchy, editable tables, and styled code blocks.",
                    tag = "Best for Editing & Submission",
                    isSelected = selectedFormat == "DOCX",
                    onClick = {
                        selectedFormat = "DOCX"
                        if (targetFileName.endsWith(".pdf")) targetFileName = targetFileName.replace(".pdf", ".docx")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Pedagogical Options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pedagogical Options", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "4 / 5 Active", fontSize = 11.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        PedagogicalCheckItem("Grounded Slide Citations & Page Refs", "Inject slide number coordinates to verify claims", citationsChecked) { citationsChecked = it }
                        PedagogicalCheckItem("Complete Answer Key & Explanations", "Step-by-step tree rebalancing deduction proofs", answerKeyChecked) { answerKeyChecked = it }
                        PedagogicalCheckItem("LaTeX Vector Math Rendering", "Preserves crisp equation scaling for high-DPI zoom", latexMathChecked) { latexMathChecked = it }
                        PedagogicalCheckItem("University Cover Page & Metadata", "Includes faculty crest, term code, and timestamp", coverPageChecked) { coverPageChecked = it }
                        PedagogicalCheckItem("Spaced Repetition Tags", "Appends #due, #invariants, #difficulty:3 labels", spacedRepetitionChecked) { spacedRepetitionChecked = it }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Target File Name Input Card
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
                            Text(text = "Target File Name", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = "Local Target", fontSize = 10.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = targetFileName,
                            onValueChange = { targetFileName = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            leadingIcon = { Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = PrimaryGreen) },
                            trailingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = TextSecondary) }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(SurfaceCanvas).padding(8.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "6", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = "Pages", fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(SurfaceCanvas).padding(8.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "10", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = "Citations", fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(SurfaceCanvas).padding(8.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "2", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = "Code Blocks", fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "⚙ Engine: LibrePDF-wasm (WebAssembly Native Sandbox)",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Compile Button
                Button(
                    onClick = {
                        val options = ExportOptions(
                            targetArtifactTitle = selectedArtifact,
                            format = selectedFormat,
                            includeGroundedCitations = citationsChecked,
                            includeAnswerKey = answerKeyChecked,
                            includeLatexMath = latexMathChecked,
                            includeUniversityCover = coverPageChecked,
                            includeSpacedRepetitionTags = spacedRepetitionChecked,
                            targetFileName = targetFileName
                        )
                        onStartCompilation(options)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("compile_export_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Compile & Export $selectedFormat (${if (selectedFormat == "PDF") "2.4 MB" else "1.1 MB"})",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Nearby Share Button
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryGreen)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Share via Nearby Peer-to-Peer", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.ArtifactSelectableCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    badge: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() }
            .then(
                if (isSelected) Modifier.border(2.dp, PrimaryGreen, RoundedCornerShape(12.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(16.dp))
                }
                if (isSelected) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 10.sp, color = TextSecondary, lineHeight = 13.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "📑 $badge", fontSize = 9.sp, color = TextSecondary)
        }
    }
}

@Composable
private fun FormatOptionCard(
    title: String,
    size: String,
    description: String,
    tag: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .then(
                if (isSelected) Modifier.border(2.dp, PrimaryGreen, RoundedCornerShape(12.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(PrimaryContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = size, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }
                    }
                    if (isSelected) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, fontSize = 11.sp, color = TextSecondary, lineHeight = 15.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Print, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = tag, fontSize = 10.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun PedagogicalCheckItem(
    title: String,
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
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TextPrimary)
            Text(text = description, fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
        }
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = PrimaryGreen)
        )
    }
}
