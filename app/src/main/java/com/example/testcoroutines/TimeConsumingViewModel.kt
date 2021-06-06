package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "launch before:$isMainThread")
            delay(3000)
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread")
        }

        Log.i(TAG, "after:$isMainThread")
        // after:true
        // launch before:false
    }

    private fun runInMainThread(){
        // 假设该方法需要运行在主线程
        Log.i(TAG, "runInMainThread:$isMainThread")
    }
}