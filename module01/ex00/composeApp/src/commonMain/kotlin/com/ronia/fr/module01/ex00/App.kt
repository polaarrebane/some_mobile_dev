package com.ronia.fr.module01.ex00

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import module01_ex00.composeapp.generated.resources.Res
import module01_ex00.composeapp.generated.resources.calendar3_event
import module01_ex00.composeapp.generated.resources.calendar3_week
import module01_ex00.composeapp.generated.resources.time_and_weather
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

enum class Destination(
    val route: String,
    val label: String,
    val icon: DrawableResource,
    val contentDescription: String
) {
    CURRENTLY("currently", "Currently", Res.drawable.time_and_weather, "Currently"),
    TODAY("today", "Today", Res.drawable.calendar3_event, "Today"),
    WEEKLY("weekly", "Weekly", Res.drawable.calendar3_week, "Weekly"),
}

@Composable
fun GeoText(s: String){
    Text(s, fontSize = 32.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun CurrentlyScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GeoText("Currently")
    }
}

@Composable
fun TodayScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GeoText("Today")
    }
}

@Composable
fun WeeklyScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GeoText("Weekly")
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.TODAY -> TodayScreen()
                    Destination.CURRENTLY -> CurrentlyScreen()
                    Destination.WEEKLY -> WeeklyScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val startDestination = Destination.CURRENTLY
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.contentDescription,
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        },
    ) {
        paddingValues ->
        AppNavHost(navController, startDestination, modifier = Modifier.padding(paddingValues))
    }
}



@Composable
@Preview
fun App() {
    MaterialTheme {
        MainScreen()
    }
}