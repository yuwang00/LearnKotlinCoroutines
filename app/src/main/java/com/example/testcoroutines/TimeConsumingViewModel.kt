package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
            sleepThreadThreeSec()
            Log.i(TAG, "launch after:$isMainThread")
        }
        Log.i(TAG, "after:$isMainThread")
        //2021-06-06 16:35:15.593 8226-8226/com.example.testcoroutines I/TimeConsumingViewModel: after:true
        //2021-06-06 16:35:15.595 8226-8311/com.example.testcoroutines I/TimeConsumingViewModel: launch before:false
        //2021-06-06 16:35:18.596 8226-8311/com.example.testcoroutines I/TimeConsumingViewModel: launch after:false
    }

    private fun sleepThreadThreeSec() {
        Thread.sleep(3000)
    }
}