package com.ronia.fr.module00.module00_ex02

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Nord0 = Color(0xFF2E3440) // Polar Night (darkest)
private val Nord1 = Color(0xFF3B4252)
private val Nord2 = Color(0xFF434C5E)
private val Nord3 = Color(0xFF4C566A)
private val Nord4 = Color(0xFFD8DEE9) // Snow Storm (lightest)
private val Nord5 = Color(0xFFE5E9F0)
private val Nord6 = Color(0xFFECEFF4)
private val Nord7 = Color(0xFF8FBCBB) // Frost (teal)
private val Nord8 = Color(0xFF88C0D0) // Frost (light blue)
private val Nord9 = Color(0xFF81A1C1) // Frost (blue)
private val Nord10 = Color(0xFF5E81AC) // Frost (dark blue)
private val Nord11 = Color(0xFFBF616A) // Aurora (red)
private val Nord12 = Color(0xFFD08770) // Aurora (orange)
private val Nord13 = Color(0xFFEBCB8B) // Aurora (yellow)
private val Nord14 = Color(0xFFA3BE8C) // Aurora (green)
private val Nord15 = Color(0xFFB48EAD) // Aurora (purple)

fun nordDarkColorScheme(): ColorScheme = ColorScheme(
    primary = Nord8,
    onPrimary = Nord0,
    primaryContainer = Nord10,
    onPrimaryContainer = Nord6,
    secondary = Nord7,
    onSecondary = Nord0,
    secondaryContainer = Nord9,
    onSecondaryContainer = Nord6,
    tertiary = Nord14,
    onTertiary = Nord0,
    tertiaryContainer = Nord7,
    onTertiaryContainer = Nord0,
    error = Nord11,
    onError = Nord0,
    errorContainer = Nord12,
    onErrorContainer = Nord0,
    background = Nord0,
    onBackground = Nord4,
    surface = Nord1,
    onSurface = Nord4,
    surfaceVariant = Nord2,
    onSurfaceVariant = Nord5,
    outline = Nord3,
    outlineVariant = Nord2,
    inverseSurface = Nord4,
    inverseOnSurface = Nord0,
    inversePrimary = Nord9,
    surfaceTint = Nord8,
    scrim = Nord0.copy(alpha = 0.8f)
)

enum class ButtonType(
    val containerColor: Color,
    val contentColor: Color,
    val weight: Float
) {
    Numeric(
        containerColor = Nord8,
        contentColor = Nord0,
        weight = 1f
    ),
    Action(
        containerColor = Nord9,
        contentColor = Nord0,
        weight = 1f
    ),
    Reset(
        containerColor = Nord11,
        contentColor = Nord4,
        weight = 1f
    ),
    Equal(
        containerColor = Nord14,
        contentColor = Nord0,
        weight = 2f
    ),
}

@Composable
fun RowScope.CalcButton(
    text: String,
    onClick: () -> Unit,
    type: ButtonType
) {

    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = Modifier
            .padding(0.dp)
            .weight(type.weight)
            .fillMaxSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = type.containerColor,
            contentColor = type.contentColor,
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

fun action(a: String) {
    println(a)
}

@Composable
fun CalculatorKeyboard() {
    Column {
        Row(modifier = Modifier.fillMaxWidth().height(48.dp))
        {
            CalcButton("7", { action("7") }, ButtonType.Numeric)
            CalcButton("8", { action("8") }, ButtonType.Numeric)
            CalcButton("9", { action("9") }, ButtonType.Numeric)
            CalcButton("C", { action("C") }, ButtonType.Reset)
            CalcButton("AC", { action("AC") }, ButtonType.Reset)
        }

        Row(modifier = Modifier.fillMaxWidth().height(48.dp))
        {
            CalcButton("4", { action("4") }, ButtonType.Numeric)
            CalcButton("5", { action("5") }, ButtonType.Numeric)
            CalcButton("6", { action("6") }, ButtonType.Numeric)
            CalcButton("+", { action("+") }, ButtonType.Action)
            CalcButton("−", { action("-") }, ButtonType.Action)
        }

        Row(modifier = Modifier.fillMaxWidth().height(48.dp))
        {
            CalcButton("1", { action("1") }, ButtonType.Numeric)
            CalcButton("2", { action("2") }, ButtonType.Numeric)
            CalcButton("3", { action("3") }, ButtonType.Numeric)
            CalcButton("×", { action("×") }, ButtonType.Action)
            CalcButton("/", { action("/") }, ButtonType.Action)
        }

        Row(modifier = Modifier.fillMaxWidth().height(48.dp))
        {
            CalcButton("0", { action("0") }, ButtonType.Numeric)
            CalcButton("•", { action(".") }, ButtonType.Numeric)
            CalcButton("00", { action("00") }, ButtonType.Numeric)
            CalcButton("=", { action("=") }, ButtonType.Equal)
        }
    }
}

@Composable
fun DisplayText(current: String, total: String) {
    Column(
       modifier = Modifier
           .fillMaxWidth()
           .padding(12.dp, 12.dp),
       horizontalAlignment = Alignment.End
    ){
        Text(current, fontWeight = FontWeight.Bold, fontSize = 32.sp, textAlign = TextAlign.Right)
        Text(total, fontWeight = FontWeight.Bold, fontSize = 32.sp, textAlign = TextAlign.Right)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DisplayText("0", "0")
        CalculatorKeyboard()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Calculator")
                },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
                colors = TopAppBarDefaults.largeTopAppBarColors()
            )
        }
    ) { innerPadding ->
        ScrollContent(innerPadding)
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = nordDarkColorScheme()
    ) {
        TopAppBar()
    }
}