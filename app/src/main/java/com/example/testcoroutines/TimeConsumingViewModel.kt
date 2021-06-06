package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

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
            executeTimeConsumingMethod()
            runInMainThread()
            Log.i(TAG, "launch after:$isMainThread")
        }

        Log.i(TAG, "after:$isMainThread")
        //2021-06-06 18:31:45.307 10618-10618/com.example.testcoroutines I/TimeConsumingViewModel: after:true
        //2021-06-06 18:31:45.309 10618-10700/com.example.testcoroutines I/TimeConsumingViewModel: launch before:false
        //2021-06-06 18:31:48.311 10618-10700/com.example.testcoroutines I/TimeConsumingViewModel: runInMainThread:false
        //2021-06-06 18:31:48.311 10618-10700/com.example.testcoroutines I/TimeConsumingViewModel: launch after:false
    }

    private fun executeTimeConsumingMethod() {
        sleep(3000)
    }

    private fun runInMainThread(){
        // 假设该方法需要运行在主线程
        Log.i(TAG, "runInMainThread:$isMainThread")
    }
}