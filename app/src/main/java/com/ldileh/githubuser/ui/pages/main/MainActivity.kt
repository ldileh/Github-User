package com.ldileh.githubuser.ui.pages.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.paging.LoadState
import com.ldileh.githubuser.base.BaseActivity
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.databinding.ActivityMainBinding
import com.ldileh.githubuser.ui.adapter.IUserAdapter
import com.ldileh.githubuser.ui.adapter.UserAdapter
import com.ldileh.githubuser.ui.pages.detail.DetailActivity
import com.ldileh.githubuser.utils.collectLatestFlow
import com.ldileh.githubuser.utils.safe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), IUserAdapter {

    private val viewModel: MainViewModel by viewModels()

    private val userAdapter: UserAdapter = UserAdapter(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observe()
        viewModel.observeDefault()
    }

    override fun ActivityMainBinding.setupView() {
        listUser.adapter = userAdapter

        // Handle Load States (Showing Progress Bar)
        userAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.refresh as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            // show message error while get trouble when load data users
            errorState?.let {
                // print the error on log
                it.error.printStackTrace()
                // show it to user
                viewModel.showMessageError(it.error.localizedMessage)
            }

            // show state of loading when load data users
            binding.viewContainer.apply {
                if (loadState.refresh is LoadState.Loading){
                    showLoading()
                }else{
                    hideLoading()
                }
            }
        }

        edtSearch.doAfterTextChanged { s ->
            viewModel.setSearchQuery(s.toString().trim())
        }
    }

    override fun onUserClicked(user: UserEntity) {
        DetailActivity
            .getIntent(this@MainActivity, user)
            .run { startActivity(this) }
    }

    private fun MainViewModel.observe(){
        users.collectLatestFlow(this@MainActivity) {
            delay(500)
            userAdapter.submitData(it)
        }
    }
}