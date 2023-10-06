package com.example.composeapp1.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeapp1.screens.CameraScreen
import com.example.composeapp1.screens.HomeScreen
import com.example.composeapp1.screens.SearchScreen
import com.example.composeapp1.viewmodels.MainViewModel

@Composable
fun MyNavigation(navController: NavHostController, vm: MainViewModel = viewModel()){
    NavHost(navController = navController, startDestination = MyScreens.HomeScreen.route){
        composable(route = MyScreens.HomeScreen.route){
            vm.setTitle(MyScreens.HomeScreen.title)
            HomeScreen(navController)
        }
        composable(route = MyScreens.SearchScreen.route){
            vm.setTitle(MyScreens.SearchScreen.title)
            SearchScreen()
        }
        composable(route = MyScreens.CameraScreen.route){
            vm.setTitle(MyScreens.CameraScreen.title)
            CameraScreen()
        }
    }
}