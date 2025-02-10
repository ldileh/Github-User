package com.ldileh.githubuser.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible

class LoadingContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val progressBar: ProgressBar

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        // Create ProgressBar (Circular)
        progressBar = ProgressBar(context).apply {
            isIndeterminate = true
            visibility = View.GONE // Default: hidden
        }

        // Add ProgressBar to this Layout
        addView(progressBar)
    }

    /** Show loading (hide content views) */
    fun showLoading() {
        progressBar.isVisible = true
        setChildrenVisibility(false)
    }

    /** Hide loading (show content views) */
    fun hideLoading() {
        progressBar.isVisible = false
        setChildrenVisibility(true)
    }

    /** Set visibility for all child views except ProgressBar */
    private fun setChildrenVisibility(isVisible: Boolean) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child != progressBar) {
                child.isVisible = isVisible
            }
        }
    }
}