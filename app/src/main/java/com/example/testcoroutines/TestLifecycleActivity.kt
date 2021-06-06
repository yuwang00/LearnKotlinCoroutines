package com.example.testcoroutines

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * author : chenmin
 * e-mail : 136214454@qq.com
 * time   : 2021/06/06
 * desc   :
 * version: 1.0
 */
class TestLifecycleActivity : AppCompatActivity() {
    private val viewModel: TimeConsumingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lifecycle)
        findViewById<TextView>(R.id.finish).setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.execute).setOnClickListener {
            viewModel.timeConsumingMethod()
        }
    }
}