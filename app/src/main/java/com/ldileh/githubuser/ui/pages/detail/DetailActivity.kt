package com.ldileh.githubuser.ui.pages.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.paging.LoadState
import com.google.android.material.appbar.AppBarLayout
import com.ldileh.githubuser.base.BaseActivity
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.databinding.ActivityDetailBinding
import com.ldileh.githubuser.ui.adapter.UserRepoAdapter
import com.ldileh.githubuser.utils.collectLatestFlow
import com.ldileh.githubuser.utils.loadAvatar
import com.ldileh.githubuser.utils.safe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.math.abs

@AndroidEntryPoint
class DetailActivity: BaseActivity<ActivityDetailBinding, DetailViewModel>(),
    AppBarLayout.OnOffsetChangedListener {

    private val viewModel: DetailViewModel by viewModels()

    private val extraData: UserEntity? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, UserEntity::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DATA)
        }
    }

    private val repoAdapter: UserRepoAdapter = UserRepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observeDefault()
        viewModel.observe()
    }

    override fun ActivityDetailBinding.setupView() {
        ivAvatar.loadAvatar(extraData?.avatarUrl.safe())

        listRepo.adapter = repoAdapter

        // Handle Load States (Showing Progress Bar)
        repoAdapter.addLoadStateListener { loadState ->
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
        }

        // Setup toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Set title
        collapsingToolbar.title = extraData?.login.safe()
        // Listen for AppBarLayout scrolling changes
        appBar.addOnOffsetChangedListener(this@DetailActivity)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val totalScrollRange = appBarLayout.totalScrollRange

        if (abs(verticalOffset) == totalScrollRange) {
            // Toolbar fully expanded (collapsed state), hide image
            binding.viewUserInfo.visibility = View.GONE
        } else {
            // Toolbar visible, show image
            binding.viewUserInfo.visibility = View.VISIBLE
        }
    }

    private fun DetailViewModel.observe(){
        getUserRepos(extraData?.login.safe()).collectLatestFlow(this@DetailActivity){
            delay(500)
            repoAdapter.submitData(it)
        }

        getUserDetail(extraData?.login.safe()).collectLatestFlow(this@DetailActivity){
            binding.tvDescription.text = it?.bio.safe()
        }

        detailLoading.collectLatestFlow(this@DetailActivity){
            binding.viewContainerUserData.apply {
                if (it){
                    showLoading()
                }else{
                    hideLoading()
                }
            }
        }
    }

    companion object{

        private const val EXTRA_DATA = "extra_data"

        fun getIntent(context: Context, data: UserEntity): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_DATA, data)
            }
        }
    }
}