package com.whistle.demo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.whistle.demo.R
import com.whistle.demo.model.ScreenState
import com.whistle.demo.viewmodel.BaseViewModel

open class BaseFragment : Fragment() {

    var needsRefreshing = false

    /**
     * Sets the proper state of the screen to display either a spinner while loading, the
     * content when received, or no content on error
     */
    protected fun setScreenState(loadState: ScreenState) {
        when (loadState) {
            ScreenState.LOADING -> showContent(false)
            ScreenState.READY -> showContent(true)
            else -> hideAllContent()
        }
    }

    // TODO: Show a dialog
    protected fun showError(viewModel: BaseViewModel) {
        Toast.makeText(context, "Error: " + viewModel.error.value, Toast.LENGTH_LONG).show()
    }

    protected fun showContent(show: Boolean) {
        activity?.findViewById<View>(R.id.progress_spinner)?.visibility = if (show) View.GONE else View.VISIBLE
        activity?.findViewById<View>(R.id.recycler_contents)?.visibility = if (show) View.VISIBLE else View.GONE
    }

    protected fun hideAllContent() {
        activity?.findViewById<View>(R.id.progress_spinner)?.visibility = View.GONE
        activity?.findViewById<View>(R.id.recycler_contents)?.visibility = View.GONE
    }
}