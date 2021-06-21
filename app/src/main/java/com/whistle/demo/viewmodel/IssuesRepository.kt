package com.whistle.demo.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whistle.demo.model.Issue
import com.whistle.demo.util.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class IssuesRepository @Inject constructor() {

    suspend fun getIssuesList() : MediatorLiveData<ResponseData<List<Issue>>> {
        val liveData = MediatorLiveData<ResponseData<List<Issue>>>()

        withContext(Dispatchers.IO) {
            var responseData = fetchResponse()
            withContext(Dispatchers.Main) {
                liveData.value = responseData
            }
        }

        return liveData
    }

    private fun fetchResponse() : ResponseData<List<Issue>> {
        val client = OkHttpClient()
        var response: Response? = null
        var returnVal: List<Issue>? = null
        var exceptionMessage: String? = null

        val url = ENDPOINT_URL.toHttpUrlOrNull()!!.newBuilder()
            .addQueryParameter("filter", "all")
            .addQueryParameter("state", "open")
            .addQueryParameter("per_page", MAX_ISSUES.toString())
        val urlString = url.build().toString()
        val request = Request.Builder()
            .url(urlString)
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

    private fun parseResponse(response: Response) : List<Issue> {
        val itemType = object : TypeToken<List<Issue>>() {}.type
        return Gson().fromJson(response.body?.charStream(), itemType)
    }

    companion object {
        val ENDPOINT_URL: String = "https://api.github.com/repos/ReactiveX/RxJava/issues"
        val MAX_ISSUES = 100
    }
}