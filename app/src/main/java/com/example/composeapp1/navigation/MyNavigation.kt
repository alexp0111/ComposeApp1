package com.example.composeapp1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composeapp1.screens.CameraScreen
import com.example.composeapp1.screens.HomeScreen
import com.example.composeapp1.screens.SearchScreen
import com.example.composeapp1.viewmodels.MainViewModel
import com.example.composeapp1.viewmodels.SharedViewModel

@Composable
fun MyNavigation(navController: NavHostController, vm: MainViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = "start") {
        navigation(
            startDestination = MyScreens.HomeScreen.route,
            route = "start"
        ) {
            composable(route = MyScreens.HomeScreen.route) { entry ->
                vm.setTitle(MyScreens.HomeScreen.title)

                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val state by viewModel.bitmaps.collectAsState()
                HomeScreen(navController, state)
            }
            composable(route = MyScreens.SearchScreen.route) {
                SearchScreen()
            }
            composable(route = MyScreens.CameraScreen.route) {entry ->
                vm.setTitle(MyScreens.SearchScreen.title)

                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val state by viewModel.bitmaps.collectAsState()
                vm.setTitle(MyScreens.CameraScreen.title)
                CameraScreen(state = state) { bitmap ->
                    viewModel.onTakePhoto(bitmap)
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
