package com.danshouseproject.project.moviecatalogue.core.components.data.remote.status

sealed class ApiResponse<out R> {
    data class Success<out T>(val body: T) : ApiResponse<T>()
    data class Empty(val msg: String) : ApiResponse<Nothing>()
    data class Error(val msg: String) : ApiResponse<Nothing>()
}