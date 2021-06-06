package com.example.testcoroutines

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 *
 * author : chenmin
 * e-mail : 136214454@qq.com
 * time   : 2021/06/06
 * desc   :
 * version: 1.0
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class LoginResponseParser {
    fun parse(inputStream: InputStream): LoginResponse {
        return LoginResponse()
    }
}

class LoginResponse {

}

private const val loginUrl = "https://example.com/login"

class LoginRepository(private val responseParser: LoginResponseParser) {

    // Function that makes the network request, blocking the current thread
    fun makeLoginRequest(
        jsonBody: String
    ): Result<LoginResponse> {
        val url = URL(loginUrl)
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(jsonBody.toByteArray())
            return Result.Success(responseParser.parse(inputStream))
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}