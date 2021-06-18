package com.danshouseproject.project.moviecatalogue.`object`.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtils {

    private const val ZERO_VAL = 0
    private const val ONE_VAL = 1
    private const val TEEN_VAL = 10

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(ONE_VAL)
        val latch = CountDownLatch(ONE_VAL)

        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data[ZERO_VAL] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }

        liveData.observeForever(observer)

        try {
            latch.await(TEEN_VAL.toLong(), TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return data[ZERO_VAL] as T
    }
}