package com.fenix.todoapp.data


/**
 * [Result] responsible for request result state
 */
sealed class Result<out T> {
    class Success<out T>(val data : T) : Result<T>()
    class Error(val e: Exception) : Result<Nothing>()
}