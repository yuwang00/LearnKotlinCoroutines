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
            sleepThreeSec()
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread")
        }

        Log.i(TAG, "after:$isMainThread")
        // launch before:true
        // after:true
    }

    private suspend fun sleepThreeSec(){
        withContext(Dispatchers.IO){
            Thread.sleep(3000)
        }
    }

    private fun runInMainThread(){
        // 假设该方法需要运行在主线程
        Log.i(TAG, "runInMainThread:$isMainThread")
    }
}