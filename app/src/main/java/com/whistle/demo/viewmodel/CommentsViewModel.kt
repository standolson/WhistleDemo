package com.whistle.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whistle.demo.model.Comment
import com.whistle.demo.model.Issue
import com.whistle.demo.model.ScreenState
import com.whistle.demo.util.ResponseData
import com.whistle.demo.util.observeOnce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

open class CommentsViewModel @Inject constructor(
    private val repository: CommentsRepository
) : BaseViewModel()
{
    var comments = MutableLiveData<List<Comment>>()

    public fun loadComments(url: String) : CommentsViewModel {
        screenState.value = ScreenState.LOADING
        viewModelScope.launch {
            repository.getComments(url).observeOnce { onRepositoryUpdated(it) }
        }
        return this
    }

    private fun onRepositoryUpdated(response: ResponseData<List<Comment>>) {
        if (response.hasStatusMessage()) {
            error.value = response.errorStatus
            screenState.value = ScreenState.ERROR
        }
        else {
            comments.value = response.data
            screenState.value = ScreenState.READY
        }
    }
}