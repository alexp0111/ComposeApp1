package com.example.composeapp1.viewmodels

import android.app.Application
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp1.MyApplication

class MainViewModel(app: Application): AndroidViewModel(app) {
    private var _screenTitle = MutableLiveData("")
    val screenTitle: LiveData<String>
        get() = _screenTitle

    private var _camController = MutableLiveData(LifecycleCameraController(getApplication<Application>().applicationContext))
    val camController: LiveData<LifecycleCameraController>
        get() = _camController

    fun setTitle(newTitle: String) {
        _screenTitle.value = newTitle
    }

    fun setController(controller: LifecycleCameraController) {
        _camController.value = controller
    }
}