package com.whistle.demo.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whistle.demo.model.Comment
import com.whistle.demo.model.Issue
import com.whistle.demo.util.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class CommentsRepository @Inject constructor() {

    suspend fun getComments(url: String) : MediatorLiveData<ResponseData<List<Comment>>>
    {
        val liveData = MediatorLiveData<ResponseData<List<Comment>>>()

        withContext(Dispatchers.IO) {
            var responseData = fetchResponse(url)

            withContext(Dispatchers.Main) {
                liveData.value = responseData
            }
        }

        return liveData
    }

    private fun fetchResponse(url: String) : ResponseData<List<Comment>> {
        val client = OkHttpClient()
        var response: Response? = null
        var returnVal: List<Comment>? = null
        var exceptionMessage: String? = null

        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()

        try {
            response = client.newCall(request).execute()
            if (response.isSuccessful)
                returnVal = parseResponse(response)
        }
        catch (e: Exception) {
            exceptionMessage = e.localizedMessage
        }

        if (returnVal != null)
            return ResponseData(returnVal)
        else if (exceptionMessage != null)
            return ResponseData(exceptionMessage)
        else
            return ResponseData(response!!.message)
    }

    private fun parseResponse(response: Response) : List<Comment> {
        val itemType = object : TypeToken<List<Comment>>() {}.type
        return Gson().fromJson(response.body?.charStream(), itemType)
    }
}