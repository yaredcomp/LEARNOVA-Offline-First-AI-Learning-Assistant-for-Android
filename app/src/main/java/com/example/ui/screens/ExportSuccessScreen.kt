package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
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
fun ExportSuccessScreen(
    options: ExportOptions,
    onOpenPdfViewer: () -> Unit,
    onBackToWorkspace: () -> Unit
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

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
                    IconButton(onClick = onBackToWorkspace) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "Export Completed",
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
                        Text(text = "● VERIFIED EDGE AI", fontSize = 10.sp, color = PrimaryGreen, fontWeight = FontWeight.Bold)
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
                // Success Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Artifact Compiled Successfully",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = TextPrimary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Generated locally in 3.6s • Ready for rigorous offline review and peer exchange",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Security pill
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceCanvas)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "SHA-256 Verified • AES-256 Stored • Zero Cloud Egress", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Target File Card
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
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(PrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "PDF/A", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = options.targetFileName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                                    Text(text = "Compiled from Lecture 04 • Cormen Section 13.2", fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            BadgePill("6 Pages")
                            BadgePill("2.38 MB")
                            BadgePill("10 Citations Grounded")
                            BadgePill("ISO 19005-2 Archival")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Document Plates Carousel
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "DOCUMENT PLATES", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary, letterSpacing = 0.5.sp)
                    Text(text = "Swipe to examine", fontSize = 10.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { DocumentPlateCard("CS-301", "P. 1", "Course Abstract") }
                    item { DocumentPlateCard("INVARIANTS", "P. 2", "Height Proofs") }
                    item { DocumentPlateCard("ROTATIONS", "P. 3", "LR Double Turn") }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Target Storage Path Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "TARGET PATH", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(PrimaryContainer).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Text(text = "Sandbox Ready", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "/Documents/Learnova/CS-301/", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = "Single-Pass WASM Pipeline • 09:44 AM", fontSize = 10.sp, color = TextSecondary)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(
                                onClick = {
                                    clipboard.setText(AnnotatedString("/Documents/Learnova/CS-301/"))
                                    Toast.makeText(context, "Path copied", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Copy", fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedButton(
                                onClick = onOpenPdfViewer,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Folder, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Browse", fontSize = 11.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Peer-to-Peer Offline AirDrop
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
                            Text(text = "❖ Peer-to-Peer Offline AirDrop", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                            Text(text = "● BLE + Wi-Fi Direct", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Exchange zero-latency copies without campus cellular data or server mediation:",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        PeerBeamItem("MK", "Marcus K.", "CS-301 Section B • 1.2m away")
                        Spacer(modifier = Modifier.height(8.dp))
                        PeerBeamItem("ST", "Sarah T. (Pixel 8)", "Study Group Alpha • 2.8m away")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Quick Action Grid (Default PDF, Sync to Anki, Wireless Print, Share Sheet)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    QuickActionCard(Icons.Default.MenuBook, "Default PDF", "System viewer", onOpenPdfViewer)
                    QuickActionCard(Icons.Default.Style, "Sync to Anki", "14 Flashcards") {
                        Toast.makeText(context, "14 flashcards exported to Anki package", Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    QuickActionCard(Icons.Default.Print, "Wireless Print", "CUPS / Mopria") {
                        Toast.makeText(context, "Sending PDF to university wireless printer", Toast.LENGTH_SHORT).show()
                    }
                    QuickActionCard(Icons.Default.Share, "Share Sheet", "Android Intent") {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Learnova Study Guide: AVL Trees Double Rotations")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Share Study Guide"))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Open Document Button
                Button(
                    onClick = onOpenPdfViewer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("open_document_pdf_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Open Document (View PDF)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "← Back to Workspace (CS-301)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onBackToWorkspace() }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun BadgePill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SurfaceCanvas)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = text, fontSize = 9.sp, color = TextSecondary)
    }
}

@Composable
private fun DocumentPlateCard(tag: String, page: String, title: String) {
    Card(
        modifier = Modifier.width(110.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = tag, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                Text(text = page, fontSize = 8.sp, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceCanvas),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "≡", fontSize = 20.sp, color = PrimaryGreen)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}

@Composable
private fun PeerBeamItem(initials: String, name: String, distance: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceCanvas)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = initials, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = distance, fontSize = 10.sp, color = TextSecondary)
            }
        }
        Button(
            onClick = {
                Toast.makeText(context, "Beaming PDF to $name via Wi-Fi Direct", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Icon(imageVector = Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "Beam", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    sub: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = TextPrimary)
            Text(text = sub, fontSize = 9.sp, color = TextSecondary)
        }
    }
}
