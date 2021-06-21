package com.whistle.demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whistle.demo.databinding.IssueListItemBinding
import com.whistle.demo.model.Issue

class IssueAdapter(val list: List<Issue>, val clickListener: IssueListener) : RecyclerView.Adapter<IssueAdapter.ViewHolder>() {

    class ViewHolder(val binding: IssueListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = IssueListItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val issue = list[position]
        viewHolder.binding.issue = issue
        viewHolder.binding.clickListener = clickListener
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount() = list.size

    fun getItem(position: Int) = list[position]
}

class IssueListener(val clickListener: (issue: Issue) -> Unit) {
    fun onClick(issue: Issue) = clickListener(issue)
}
