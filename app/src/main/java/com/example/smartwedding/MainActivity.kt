package com.example.smartwedding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timer().schedule(timerTask { startActivity(Intent(applicationContext,
            DashboardActivity::class.java)) }, 1000)
    }
}