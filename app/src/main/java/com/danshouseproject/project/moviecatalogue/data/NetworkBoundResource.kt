package com.danshouseproject.project.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.danshouseproject.project.moviecatalogue.data.remote.status.ApiResponse
import com.danshouseproject.project.moviecatalogue.data.remote.status.ResponseStatus
import com.danshouseproject.project.moviecatalogue.helper.utils.AppExecutors
import com.danshouseproject.project.moviecatalogue.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)

            if (shouldFetch(data))
                fetchFromNetwork(dbSource)
            else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }


    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    @Suppress("UNCHECKED_CAST")
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                ResponseStatus.SUCCESS ->
                    mExecutors.diskIO.execute {
                        saveCallResult(response.body as RequestType)
                        mExecutors.mainThread.execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                ResponseStatus.EMPTY -> mExecutors.mainThread.execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.success(newData)
                    }
                }
                ResponseStatus.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(newData, response.message.toString())
                    }
                }
            }
        }
    }

    val asLiveData: LiveData<Resource<ResultType>>
        get() = result

}