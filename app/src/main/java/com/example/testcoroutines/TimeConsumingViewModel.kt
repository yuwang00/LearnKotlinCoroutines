package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            val result = async { sleepThreeSec() }
            val secResult = async { sleepTwoSec() }
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread")
        }

        Log.i(TAG, "after:$isMainThread")
        // 并未执行，注意时间戳
        // 2021-06-09 19:16:07.630 14637-14637/com.example.testcoroutines I/TimeConsumingViewModel: launch before:true
        // 2021-06-09 19:16:07.646 14637-14637/com.example.testcoroutines I/TimeConsumingViewModel: runInMainThread:true
        // 2021-06-09 19:16:07.646 14637-14637/com.example.testcoroutines I/TimeConsumingViewModel: launch after:true
        // 2021-06-09 19:16:07.682 14637-14637/com.example.testcoroutines I/TimeConsumingViewModel: after:true
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