package com.whistle.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whistle.demo.R
import com.whistle.demo.model.Comment
import com.whistle.demo.viewmodel.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentsFragment : BaseFragment() {

    lateinit var rootView: View
    @Inject lateinit var viewModel: CommentsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.setTitle(R.string.comments_fragment_title)
        rootView = LayoutInflater.from(activity).inflate(R.layout.common_recycler_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't reload on rotation
        needsRefreshing = savedInstanceState == null

        viewModel.run {
            screenState.observe(viewLifecycleOwner, Observer { setScreenState(it) })
            comments.observe(viewLifecycleOwner, Observer { showComments(it) })
            error.observe(viewLifecycleOwner, Observer { showError(viewModel) })
        }

        val url = arguments?.getString(COMMENTS_URL)
        if (url != null && needsRefreshing) {
            viewModel.loadComments(url)
            needsRefreshing = false
        }
    }

    private fun showComments(items: List<Comment>) {
        Toast.makeText(context,
            "Received " + items.size + " items", Toast.LENGTH_LONG).show()

        val recyclerView = rootView.findViewById(R.id.recycler_contents) as RecyclerView
        val adapter = CommentAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    companion object {
        val COMMENTS_URL = "COMMENTS_URL"
    }
}