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
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread,result:$result")
        }

        Log.i(TAG, "after:$isMainThread")
        // launch before:true
        // after:true
        // runInMainThread:true
        // launch after:true,result:1
    }

    private suspend fun sleepThreeSec(): Int {
        return withContext(Dispatchers.IO) {
            delay(3000)
            // 假设耗时三秒计算出结果
            1
        }
    }

    private fun runInMainThread() {
        // 假设该方法需要运行在主线程
        Log.i(TAG, "runInMainThread:$isMainThread")
    }
}