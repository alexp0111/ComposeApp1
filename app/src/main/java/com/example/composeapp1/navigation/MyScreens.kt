package com.example.composeapp1.navigation

import com.example.composeapp1.R

sealed class MyScreens(var route: String, var title: String) {
    object HomeScreen : MyScreens("home", "Home screen")
    object CameraScreen : MyScreens("camera", "Camera screen")
    object SearchScreen : MyScreens("search", "Search screen")
}