package com.example.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tester(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun Tester(
    modifier: Modifier = Modifier
) {
    val anim1 = remember {
        TargetBasedAnimation(
            animationSpec = tween(1000),
            typeConverter = Float.VectorConverter, initialValue = 10f, targetValue = 3000f
        )
    }
    val anim2 = remember { TargetBasedAnimation(animationSpec = tween(1000), typeConverter = Float.VectorConverter, initialValue = 10f, targetValue = 3000f) }
    val anim3 = remember { TargetBasedAnimation(animationSpec = tween(1000), typeConverter = Float.VectorConverter, initialValue = 10f, targetValue = 3000f) }
    var playTime1 by remember { mutableStateOf(0L) }
    var playTime2 by remember { mutableStateOf(0L) }
    var playTime3 by remember { mutableStateOf(0L) }
    var animationValue1 by remember { mutableStateOf(0) }
    var animationValue2 by remember { mutableStateOf(0) }
    var animationValue3 by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        async {
            val startTime = withFrameNanos { it }
            do {
                playTime1 = withFrameNanos { it } - startTime
                animationValue1 = anim1.getValueFromNanos(playTime1).toInt()
            } while (!anim1.isFinishedFromNanos(playTime1))
        }
        delay(500L)
        async {
            val startTime = withFrameNanos { it }
            do {
                playTime2 = withFrameNanos { it } - startTime
                animationValue2 = anim1.getValueFromNanos(playTime2).toInt()
            } while (!anim2.isFinishedFromNanos(playTime2))
        }
        delay(500L)
        async {
            val startTime = withFrameNanos { it }
            do {
                playTime3 = withFrameNanos { it } - startTime
                animationValue3 = anim3.getValueFromNanos(playTime3).toInt()
            } while (!anim1.isFinishedFromNanos(playTime3))
        }
    }
    Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier) {
            drawCircle(
                color = Color.Red,
                radius = size.width / 11 + animationValue1,
                center = center
            )
            drawCircle(
                color = Color.Blue,
                radius = size.width / 9 + animationValue2,
                center = center
            )
            drawCircle(
                color = Color.Yellow,
                radius = size.width / 7 + animationValue3,
                center = center
            )
        }
    }
}
