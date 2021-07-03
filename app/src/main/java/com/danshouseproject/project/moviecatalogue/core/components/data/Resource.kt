package com.danshouseproject.project.moviecatalogue.core.components.data

sealed class Resource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T?, msg: String) : Resource<T>(data, msg)
}