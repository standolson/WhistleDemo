package com.whistle.demo.util

/**
 * Helper class that holds both response data and an error.  Allows repository classes
 * to pass an error back to the UI for display.
 */
class ResponseData<D>
{
    var data: D? = null
    var errorStatus: String? = null

    constructor(resultStatus: String?) {
        this.errorStatus = resultStatus
    }

    constructor(data: D) {
        this.data = data
    }

    public fun hasStatusMessage() : Boolean = errorStatus != null
}