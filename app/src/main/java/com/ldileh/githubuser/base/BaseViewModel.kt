package com.ldileh.githubuser.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel: ViewModel() {

    protected val _messageError: MutableStateFlow<String?> = MutableStateFlow(null)
    val messageError: Flow<String?> = _messageError

    fun showMessageError(message: String?){
        _messageError.value = message
    }
}