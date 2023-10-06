package com.example.composeapp1.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen() {
    Surface(
        Modifier.fillMaxSize(),
    ) {
        Text(text = "SearchScreen")
    }
}