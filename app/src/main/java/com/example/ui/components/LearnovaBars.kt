package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
fun LearnovaTopBar(
    isAmharic: Boolean,
    onToggleLanguage: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenModelManager: () -> Unit
) {
    Surface(
        color = SurfaceCanvas,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // University Crest / Learnova brand mark
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✦",
                    color = PrimaryGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = if (isAmharic) "ዲላ ዩኒቨርሲቲ • አካዳሚክ" else "ACADEMIC EDITION",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Learnova",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified Academic",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // On-Device status pill (tappable to open Model Manager)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(PrimaryContainer)
                    .clickable { onOpenModelManager() }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("on_device_pill"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "On-device",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryGreen
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Language Toggle Chip: EN / አማ
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(SurfaceCard)
                    .clickable { onToggleLanguage() }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EN",
                    fontSize = 11.sp,
                    fontWeight = if (!isAmharic) FontWeight.Bold else FontWeight.Normal,
                    color = if (!isAmharic) PrimaryGreen else TextSecondary
                )
                Text(
                    text = " | ",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
                Text(
                    text = "አማ",
                    fontSize = 11.sp,
                    fontWeight = if (isAmharic) FontWeight.Bold else FontWeight.Normal,
                    color = if (isAmharic) PrimaryGreen else TextSecondary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Profile / Settings Avatar
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .clickable { onOpenSettings() }
                    .testTag("profile_avatar"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Settings and Profile",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

enum class NavDestination(val label: String, val amharicLabel: String) {
    HOME("Home", "መነሻ"),
    WORKSPACES("Workspaces", "ክፍሎች"),
    CHAT("Chat", "ውይይት"),
    STUDY("Study", "ጥናት"),
    LIBRARY("Library", "ቤተ-መጽሐፍት")
}

@Composable
fun LearnovaBottomBar(
    currentDestination: NavDestination,
    isAmharic: Boolean,
    onNavigate: (NavDestination) -> Unit
) {
    NavigationBar(
        modifier = Modifier.height(72.dp),
        containerColor = SurfaceCanvas,
        tonalElevation = 4.dp
    ) {
        val items = listOf(
            Triple(NavDestination.HOME, Icons.Filled.Home, Icons.Outlined.Home),
            Triple(NavDestination.WORKSPACES, Icons.Filled.Folder, Icons.Outlined.Folder),
            Triple(NavDestination.CHAT, Icons.Filled.Chat, Icons.Outlined.Chat),
            Triple(NavDestination.STUDY, Icons.Filled.School, Icons.Outlined.School),
            Triple(NavDestination.LIBRARY, Icons.Filled.LocalLibrary, Icons.Outlined.LocalLibrary)
        )

        items.forEach { (dest, filledIcon, outlinedIcon) ->
            val isSelected = currentDestination == dest
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(dest) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) filledIcon else outlinedIcon,
                        contentDescription = dest.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = if (isAmharic) dest.amharicLabel else dest.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryGreen,
                    selectedTextColor = PrimaryGreen,
                    indicatorColor = PrimaryContainer,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}
