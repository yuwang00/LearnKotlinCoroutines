package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * author : chenmin
 * e-mail : 136214454@qq.com
 * time   : 2021/06/06
 * desc   : 耗时方法ViewModel
 * version: 1.0
 */
private const val TAG = "TimeConsumingViewModel"

class TimeConsumingViewModel : ViewModel() {
    private val isMainThread
        get() = Looper.getMainLooper() == Looper.myLooper()

    fun timeConsumingMethod() {
        viewModelScope.launch {
            Log.i(TAG, "launch before:$isMainThread")
            val firJob = async { sleepThreeSec() }
            val secJob = async { sleepTwoSec() }
            listOf(firJob, secJob).awaitAll()
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread")
        }

        Log.i(TAG, "after:$isMainThread")
        // 已执行，注意时间戳
        // 2021-06-21 20:50:51.769 17505-17505/com.example.testcoroutines I/TimeConsumingViewModel: launch before:true
        // 2021-06-21 20:50:51.775 17505-17505/com.example.testcoroutines I/TimeConsumingViewModel: after:true
        // 2021-06-21 20:50:54.780 17505-17505/com.example.testcoroutines I/TimeConsumingViewModel: runInMainThread:true
    }

    private suspend fun sleepThreeSec(): Int {
        return withContext(Dispatchers.IO) {
            delay(3000)
            // 假设耗时三秒计算出结果
            1
        }
    }

    private suspend fun sleepTwoSec(): Int {
        return withContext(Dispatchers.IO) {
            delay(2000)
            // 假设耗时两秒计算出结果
            2
        }
    }

    private fun runInMainThread() {
        // 假设该方法需要运行在主线程
        Log.i(TAG, "runInMainThread:$isMainThread")
    }
}