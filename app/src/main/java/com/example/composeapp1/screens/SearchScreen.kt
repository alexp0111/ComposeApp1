package com.example.composeapp1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeapp1.MyApplication
import com.example.composeapp1.R
import com.example.composeapp1.utils.DiskLoader
import com.example.composeapp1.utils.URLoader
import com.example.composeapp1.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



@Composable
fun SearchScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Surface(
            Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.basic_padding)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var textFiled by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = textFiled,
                    onValueChange = {
                        textFiled = it
                    },
                    label = {
                        Text(text = "Image url")
                    }
                )
                Button(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.basic_padding)),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = URLoader.loadPhotoByUrl(
                                textFiled,
                                mainViewModel.getApplication<MyApplication>().applicationContext
                            ) { btmp ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    DiskLoader.loadIntoMemo(
                                        btmp,
                                        mainViewModel.getApplication<MyApplication>().applicationContext
                                    )
                                }
                            }
                            snackbarHostState.showSnackbar(result)
                        }
                    }
                ) {
                    Text(text = "Download")
                }
            }
        }
    }
}
