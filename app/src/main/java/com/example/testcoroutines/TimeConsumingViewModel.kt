package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
            val result = sleepThreeSec()
            val secResult = sleepTwoSec()
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread,result:${result + secResult}")
        }

        Log.i(TAG, "after:$isMainThread")
        // 串行执行，注意时间戳
        // 2021-06-09 19:03:39.087 14178-14178/com.example.testcoroutines I/TimeConsumingViewModel: launch before:true
        // 2021-06-09 19:03:39.101 14178-14178/com.example.testcoroutines I/TimeConsumingViewModel: after:true
        // 2021-06-09 19:03:44.123 14178-14178/com.example.testcoroutines I/TimeConsumingViewModel: runInMainThread:true
        // 2021-06-09 19:03:44.124 14178-14178/com.example.testcoroutines I/TimeConsumingViewModel: launch after:true,result:3
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
            // 假设耗时三秒计算出结果
            2
        }
    }

    private fun runInMainThread() {
        // 假设该方法需要运行在主线程
        Log.i(TAG, "runInMainThread:$isMainThread")
    }
}