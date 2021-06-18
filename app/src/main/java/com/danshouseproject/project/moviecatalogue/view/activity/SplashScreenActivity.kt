package com.danshouseproject.project.moviecatalogue.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runThread(this)
    }

    private fun runThread(context: Context) {
        val thread: Thread = object : Thread() {
            override fun run() {
                super.run()
                try {
                    sleep(resources.getInteger(R.integer.sleep_in_ms).toLong())
                } catch (exception: InterruptedException) {
                    exception.printStackTrace()
                } finally {
                    startActivity(Intent(context, HomeActivity::class.java))
                    finish()
                }
            }
        }
        thread.start()
    }
}