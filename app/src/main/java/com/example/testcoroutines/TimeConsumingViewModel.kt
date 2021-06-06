package com.example.testcoroutines

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "launch1 before:$isMainThread")
            sleepThreadThreeSec()
            Log.i(TAG, "launch1 after:$isMainThread")
        }
        Log.i(TAG, "after:$isMainThread")
        //2021-06-06 17:40:08.891 9643-9643/com.example.testcoroutines I/TimeConsumingViewModel: after:true
        //2021-06-06 17:40:08.892 9643-9722/com.example.testcoroutines I/TimeConsumingViewModel: launch1 before:false
        //2021-06-06 17:40:08.892 9643-9721/com.example.testcoroutines I/TimeConsumingViewModel: launch before:false
        //2021-06-06 17:40:11.893 9643-9721/com.example.testcoroutines I/TimeConsumingViewModel: launch after:false
        //2021-06-06 17:40:11.894 9643-9722/com.example.testcoroutines I/TimeConsumingViewModel: launch1 after:false
    }

    private fun sleepThreadThreeSec() {
        Thread.sleep(3000)
    }
}