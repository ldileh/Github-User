package com.ldileh.githubuser.ui.pages.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ldileh.githubuser.base.BaseViewModel
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.data.repository.GithubRepositories
import com.ldileh.githubuser.models.response.User
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    githubRepositories: GithubRepositories
): BaseViewModel() {

    val users: Flow<PagingData<UserEntity>> =
        githubRepositories.getUsers().cachedIn(viewModelScope)

//    private val _itemsUsers: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
//    val itemsUsers: Flow<List<User>> = _itemsUsers
//
//    init {
//        getUsers(1)
//    }
//
//    fun getUsers(page: Int){
//        viewModelScope.launch(Dispatchers.IO) {
//            githubRepositories
//                .getUsers()
//                .suspendOnSuccess {
//                    _itemsUsers.value = data
//                }
//                .suspendOnError {
//                    _messageError.value = message()
//                }
//                .suspendOnException {
//                    _messageError.value = message()
//                }
//        }
//    }
}