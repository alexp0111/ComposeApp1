package com.example.composeapp1.screens

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeapp1.viewmodels.MainViewModel


@Composable
fun CameraScreen(
    mainViewModel: MainViewModel = viewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val controller = mainViewModel.camController.value

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(
            controller = controller,
            lifecycleOwner = lifecycleOwner,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = {
                    controller?.cameraSelector =
                        if (controller?.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else CameraSelector.DEFAULT_BACK_CAMERA
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = "Switch camera"
                )
            }
            IconButton(
                onClick = {
                    controller?.let {
                        takePhoto(
                            controller = it,
                            onPhotoTaken = {

                            }
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take photo"
                )
            }
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController?,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier
) {
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller?.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    // controller.takePicture(
    //     ContextCompat.getMainExecutor(applicationContext),
    //     object : OnImageCapturedCallback() {
    //         override fun onCaptureSuccess(image: ImageProxy) {
    //             super.onCaptureSuccess(image)
//
    //             val matrix = Matrix().apply {
    //                 postRotate(image.imageInfo.rotationDegrees.toFloat())
    //             }
    //             val rotatedBitmap = Bitmap.createBitmap(
    //                 image.toBitmap(),
    //                 0,
    //                 0,
    //                 image.width,
    //                 image.height,
    //                 matrix,
    //                 true
    //             )
//
    //             onPhotoTaken(rotatedBitmap)
    //         }
//
    //         override fun onError(exception: ImageCaptureException) {
    //             super.onError(exception)
    //             Log.e("Camera", "Couldn't take photo: ", exception)
    //         }
    //     }
    // )
}
