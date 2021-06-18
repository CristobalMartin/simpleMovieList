package com.cristobal.simplemovielist.repository

sealed class LoadState<out R> {
    data class Success<out T>(val data: T) : LoadState<T>()
    class Error(val msg: String? = null) : LoadState<Nothing>()
    object Loading : LoadState<Nothing>()
    object Unstarted : LoadState<Nothing>()
}