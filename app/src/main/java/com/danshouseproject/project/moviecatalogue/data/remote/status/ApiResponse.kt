package com.danshouseproject.project.moviecatalogue.data.remote.status

class ApiResponse<T>(val status: ResponseStatus, val body: T?, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> =
            ApiResponse(ResponseStatus.SUCCESS, body, null)

        fun <T> empty(body: T, msg: String): ApiResponse<T> =
            ApiResponse(ResponseStatus.EMPTY, body, msg)

        fun <T> error(msg: String): ApiResponse<T> =
            ApiResponse(ResponseStatus.ERROR, null, msg)
    }
}