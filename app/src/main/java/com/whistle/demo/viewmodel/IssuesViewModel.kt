package com.whistle.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whistle.demo.util.ResponseData
import com.whistle.demo.model.Issue
import com.whistle.demo.model.ScreenState
import com.whistle.demo.util.observeOnce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

open class IssuesViewModel @Inject constructor(
    private val repository: IssuesRepository
) : BaseViewModel() {

    var issues = MutableLiveData<List<Issue>>()

    fun loadIssues() : IssuesViewModel {
        screenState.value = ScreenState.LOADING
        viewModelScope.launch {
            repository.getIssuesList().observeOnce { onRepositoryUpdated(it) }
        }
        return this
    }

    private fun onRepositoryUpdated(response: ResponseData<List<Issue>>) {
        if (response.hasStatusMessage()) {
            error.value = response.errorStatus
            screenState.value = ScreenState.ERROR
        }
        else {
            issues.value = response.data
            screenState.value = ScreenState.READY
        }
    }
}