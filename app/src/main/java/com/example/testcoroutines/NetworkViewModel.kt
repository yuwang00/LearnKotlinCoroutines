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
 * desc   :
 * version: 1.0
 */
private const val TAG = "NetworkViewModel"

class NetworkViewModel : ViewModel() {
    private val loginRepository = LoginRepository(LoginResponseParser())
    fun login(username: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "launch:${Looper.getMainLooper() == Looper.myLooper()}")
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            loginRepository.makeLoginRequest(jsonBody)
        }
        Log.i(TAG, "after:${Looper.getMainLooper() == Looper.myLooper()}")
    }
}