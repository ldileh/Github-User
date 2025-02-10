package com.ldileh.githubuser.ui.pages.detail

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ldileh.githubuser.base.BaseViewModel
import com.ldileh.githubuser.data.local.entity.RepoEntity
import com.ldileh.githubuser.data.repository.GithubRepositories
import com.ldileh.githubuser.models.response.UserDetail
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.sandwich.toFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubRepositories: GithubRepositories
): BaseViewModel() {

    private val _detailLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val detailLoading: Flow<Boolean> = _detailLoading


    @OptIn(FlowPreview::class)
    fun getUserRepos(username: String): Flow<PagingData<RepoEntity>> {
        return githubRepositories.getUserRepos(username)
            .debounce(250)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
    }

    fun getUserDetail(username: String): Flow<UserDetail?> = flow {
        githubRepositories
            .getUserDetail(username)
            .suspendOnSuccess {
                _detailLoading.value = false
                emit(data)
            }
            .suspendOnError {
                _detailLoading.value = false
                _messageError.value = message()
            }
            .suspendOnException {
                _detailLoading.value = false
                _messageError.value = message()
            }
            .toFlow().flowOn(Dispatchers.IO)
    }
}