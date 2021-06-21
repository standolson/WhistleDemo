package com.whistle.demo.model

import com.google.gson.annotations.SerializedName

class Issue {
    var title: String? = null
    @SerializedName("comments_url") var commentsUrl: String? = null
}