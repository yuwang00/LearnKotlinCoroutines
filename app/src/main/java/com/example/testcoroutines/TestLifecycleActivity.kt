package com.example.testcoroutines

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * author : chenmin
 * e-mail : 136214454@qq.com
 * time   : 2021/06/06
 * desc   :
 * version: 1.0
 */
private const val TAG = "TestLifecycleActivity"

class TestLifecycleActivity : AppCompatActivity() {
    private val viewModel: TimeConsumingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lifecycle)
        val job = lifecycle.coroutineScope.launch {
            delay(5000)
            Log.i(TAG, "job finished")
        }
        findViewById<TextView>(R.id.finish).setOnClickListener {
            Log.i(TAG, "button click")
            // just print button click
            job.cancel()
        }
        // print job finished
        findViewById<TextView>(R.id.execute).setOnClickListener {
            viewModel.timeConsumingMethod()
        }
    }
}