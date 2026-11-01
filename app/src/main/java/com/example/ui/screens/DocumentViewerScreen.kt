package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ErrorDestructive
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SurfaceCanvas
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningNotice

@Composable
fun DocumentViewerScreen(
    slideNumber: Int = 14,
    onBack: () -> Unit,
    onExplainWithAI: () -> Unit,
    onCreateQuiz: () -> Unit
) {
    var showGroundingDrawer by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SurfaceCanvas
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Reader Bar
            Surface(
                color = SurfaceCanvas,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Pdf Reader",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "p. 14 / 42 • ● Offline",
                            fontSize = 11.sp,
                            color = PrimaryGreen
                        )
                    }
                    IconButton(onClick = {}) { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = TextPrimary) }
                    IconButton(onClick = {}) { Icon(imageVector = Icons.Default.Edit, contentDescription = "Annotate", tint = TextPrimary) }
                    IconButton(onClick = {}) { Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More", tint = TextPrimary) }
                }
            }

            // Sub-header Metadata Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceCard)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(PrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "PDF", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "CS301_AVL_Trees_Stud...",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Page 4 of 6 • 100% Zoom",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(PrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🛡 Edge Engine • Zero Egress",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }

            // Document Scrollable Canvas
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Main Academic White Paper Sheet
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "DEPARTMENT OF COMPUTER SCIENCE • C...P. 104",
                            fontSize = 10.sp,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "SECTION IV: SELF-BALANCING BINARY TREES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "§ 4.2 Invariant Restoration via Left-Right (LR) Double Rotation",
                            fontSize = 18.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Lemma 4.1 with Midterm Focus Highlight
                        Text(
                            text = "Lemma 4.1. Let node A have balance factor BF(A) = +2 and its left descendant child B have balance factor BF(B) = -1. ",
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            fontFamily = FontFamily.Serif,
                            color = TextPrimary
                        )

                        // Highlighted passage
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(WarningNotice.copy(alpha = 0.35f))
                                .padding(8.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFFD97706))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "✍ Midterm Focus",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "A single right rotation on Node A cannot rebalance the subtree when the inner subtree is heavy. The inner imbalance preserves an asymmetrical depth differential that causes child parity violations upon singular permutation.",
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = TextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Rendered LaTeX equation card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceCanvas)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "∑", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Balance Factor Height Invariant Eq. (4.3)",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                    }
                                    Text(text = "LaTeX Rendered", fontSize = 10.sp, color = TextSecondary)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(SurfaceCard)
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "BF(A) = h(T_L) - h(T_R) = +2",
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = PrimaryGreen
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "h(Subtree_C) = h_max - 1",
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 12.sp,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Grounded in Lecture 04 chip
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryContainer)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = PrimaryGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "✦ Grounded in Lecture 04 (Slide 14) • 98.4% Match",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = PrimaryGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Vector Diagram of Left-Right Double Rotation
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceCanvas)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "FIGURE 4.2: LEFT-RIGHT (LR) DOUBLE ROTATION TOPOLOGY",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // Compose Canvas drawing the tree before and after
                                Canvas(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                ) {
                                    val w = size.width
                                    val h = size.height

                                    // Before Rotation (Left side)
                                    val nodeA = Offset(w * 0.25f, h * 0.25f)
                                    val nodeB = Offset(w * 0.15f, h * 0.55f)
                                    val nodeC = Offset(w * 0.22f, h * 0.85f)

                                    drawLine(Color.Gray, nodeA, nodeB, strokeWidth = 3f)
                                    drawLine(Color.Gray, nodeB, nodeC, strokeWidth = 3f)

                                    drawCircle(Color(0xFFBA1A1A), radius = 16f, center = nodeA)
                                    drawCircle(Color.LightGray, radius = 14f, center = nodeB)
                                    drawCircle(Color(0xFF1B7A3E), radius = 14f, center = nodeC)

                                    // Arrow in center
                                    val arrowStart = Offset(w * 0.45f, h * 0.5f)
                                    val arrowEnd = Offset(w * 0.55f, h * 0.5f)
                                    drawLine(Color(0xFF1B7A3E), arrowStart, arrowEnd, strokeWidth = 5f)

                                    // After Rotation (Right side)
                                    val rootC = Offset(w * 0.75f, h * 0.3f)
                                    val childB = Offset(w * 0.65f, h * 0.7f)
                                    val childA = Offset(w * 0.85f, h * 0.7f)

                                    drawLine(Color.Gray, rootC, childB, strokeWidth = 3f)
                                    drawLine(Color.Gray, rootC, childA, strokeWidth = 3f)

                                    drawCircle(Color(0xFF1B7A3E), radius = 16f, center = rootC)
                                    drawCircle(Color.LightGray, radius = 14f, center = childB)
                                    drawCircle(Color.LightGray, radius = 14f, center = childA)
                                }

                                Text(
                                    text = "Node C is promoted to local sub-root; height decreases by exactly 1 unit.",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grounded Citation Analysis Card
                if (showGroundingDrawer) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "✦", color = PrimaryGreen, fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "Grounded Citation Analysis",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = "CS-301 Lecture 4: Balance Invariants (Slide 14)",
                                            fontSize = 11.sp,
                                            color = TextSecondary
                                        )
                                    }
                                }
                                IconButton(onClick = { showGroundingDrawer = false }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceCanvas)
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "🎓 Prof. Vance's Lecture Transcription • 38m:12s",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = PrimaryGreen
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "\"Pay close attention to Lemma 4.1 when preparing for Midterm 1. Compare this with Cormen et al. Theorem 13.2: the inner rotation must happen before the root rotation, or the tree loses binary search invariant validity.\"",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Serif,
                                        lineHeight = 17.sp,
                                        color = TextPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = onExplainWithAI,
                                    modifier = Modifier.weight(1.2f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                                ) {
                                    Text(
                                        text = "Explain with Needle3",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = onCreateQuiz,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                                ) {
                                    Text(
                                        text = "+ Flashcard",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryGreen
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom Navigation Toolbar
            Surface(
                color = SurfaceCard,
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Prev page") }
                        Text(text = "4 / 6", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Next page") }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.PanTool, contentDescription = "Pan") }
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.Edit, contentDescription = "Highlight", tint = PrimaryGreen) }
                        IconButton(onClick = {}) { Icon(imageVector = Icons.Default.FitScreen, contentDescription = "Fit") }
                        IconButton(onClick = onExplainWithAI) { Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = "AI", tint = PrimaryGreen) }
                    }
                }
            }
        }
    }
}
