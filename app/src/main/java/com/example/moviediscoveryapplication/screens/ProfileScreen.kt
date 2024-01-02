package com.example.moviediscoveryapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviediscoveryapplication.BottomBarScreen
import com.example.moviediscoveryapplication.R
import com.example.moviediscoveryapplication.components.RoundedButton
import com.example.moviediscoveryapplication.components.TopBarComponent
import com.example.moviediscoveryapplication.viewmodel.ProfileScreenViewModel

@Composable
fun ProfileScreen(navController: NavHostController) {

    val viewModel: ProfileScreenViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Profile"
            ) {
                navController.navigate(BottomBarScreen.Home.route) {
                    popUpTo(BottomBarScreen.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileUser()
                Spacer(modifier = Modifier.height(42.dp))
                ProfileSection("Account", viewModel, "Change Password")
                Spacer(modifier = Modifier.height(42.dp))
                ProfileSection("General", viewModel, "Notification", "Language", "Clear Cache")
                Spacer(modifier = Modifier.height(42.dp))
                ProfileSection("More", viewModel, "Help & Feedback", "About Us")
                Spacer(modifier = Modifier.height(42.dp))
                RoundedButton(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Log out",
                    filled = false
                )
            }
        }
    }
}

@Composable
fun ProfileUser() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(
                1.dp, MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(size = 180.dp))
                .background(Color.LightGray),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "profile image"
        )
        Column {
            Text(
                text = "Name",
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            )

            CompositionLocalProvider(LocalContentColor provides Color.LightGray) {
                Text(
                    text = "E-mail",
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                )
            }
        }
    }
}

@Composable
fun ProfileSection(sectionName: String, viewModel: ProfileScreenViewModel, vararg itemNames: String) {
    Column(
        modifier = Modifier
            .border(BorderStroke(
                1.dp, MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = sectionName,
            fontWeight = FontWeight.SemiBold,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))

        (itemNames.indices).forEach { index ->
            ProfileItem(
                itemName = itemNames[index],
                resource = viewModel.mapIcon(itemName = itemNames[index]))

            if (index < itemNames.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                )
            }
        }
    }
}

@Composable
fun ProfileItem(itemName: String, resource: Int?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        resource?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 22.dp),
            text = itemName,
            fontWeight = FontWeight.Medium,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            color = MaterialTheme.colorScheme.onBackground,
            softWrap = true
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .size(24.dp)
                .padding(start = 8.dp)
        )
    }
}