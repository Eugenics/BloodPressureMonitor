package com.eugenics.bloodpressuremonitor.ui.compose.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.ui.navigation.Screen
import kotlinx.coroutines.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun Splash(navController: NavHostController) {

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            var targetValue by remember { mutableStateOf(0f) }
            val alpha: Float by animateFloatAsState(
                targetValue = targetValue,
                animationSpec = tween(1000),
                finishedListener = {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                }
            )

            Image(
                painter = painterResource(R.drawable.ic_app),
                contentDescription = null,
                modifier = Modifier
                    .alpha(alpha)
                    .size(400.dp)
            )

            SideEffect {
                targetValue = 1f
            }
        }
    }
}

@Preview
@Composable
private fun SplashPreview() {
    Splash(navController = rememberNavController())
}