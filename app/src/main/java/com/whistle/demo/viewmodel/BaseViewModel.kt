package com.whistle.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whistle.demo.model.ScreenState

open class BaseViewModel : ViewModel() {

    var screenState = MutableLiveData<ScreenState>()
    var error = MutableLiveData<String>()
}