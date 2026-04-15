package com.ronia.fr.module00.ex01

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

import module00_ex01.composeapp.generated.resources.Res
import module00_ex01.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val defaultLabelText = "A  simple text"
    val helloLabelText = "Hello World"
    val buttonText = "Click me"
    var isHello by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(if(isHello) helloLabelText else defaultLabelText,
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        // Olive
                        Color.hsl(60f, 0.78f, 0.28f, 1f)
                    )
                    .padding(16.dp)
            )

            Button(onClick = {
                isHello = !isHello
            })
            {
                Text(buttonText)
            }
        }
    }
}