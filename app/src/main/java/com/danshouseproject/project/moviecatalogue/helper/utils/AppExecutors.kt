package com.danshouseproject.project.moviecatalogue.helper.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
    private val _diskIO: Executor,
    private val _networkIO: Executor,
    private val _mainThread: Executor
) {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    val diskIO: Executor
        get() = _diskIO

    val networkIO: Executor
        get() = _networkIO

    val mainThread: Executor
        get() = _mainThread


    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(cmd: Runnable) {
            mainThreadHandler.post(cmd)
        }
    }


    companion object {
        private const val THREAD_COUNT = 3
    }
}