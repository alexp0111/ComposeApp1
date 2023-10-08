package com.example.composeapp1.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composeapp1.data.MyPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(app: Application): AndroidViewModel(app) {
    private var _screenTitle = MutableLiveData("")
    val screenTitle: LiveData<String>
        get() = _screenTitle

    private var _camController = MutableLiveData(LifecycleCameraController(getApplication<Application>().applicationContext))
    val camController: LiveData<LifecycleCameraController>
        get() = _camController


    private val _photos = MutableStateFlow<List<Pair<Bitmap, MyPhoto>>>(emptyList())
    val photos = _photos.asStateFlow()

    fun setTitle(newTitle: String) {
        _screenTitle.value = newTitle
    }

    fun setController(controller: LifecycleCameraController) {
        _camController.value = controller
    }

    fun updatePhotos(list: List<Pair<Bitmap, MyPhoto>>) {
        _photos.value = list
    }
}