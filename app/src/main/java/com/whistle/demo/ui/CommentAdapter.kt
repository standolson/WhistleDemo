package com.whistle.demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whistle.demo.databinding.CommentListItemBinding
import com.whistle.demo.model.Comment

class CommentAdapter(val list: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : CommentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = CommentListItemBinding.inflate(inflater, viewGroup, false)
        return CommentAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CommentAdapter.ViewHolder, position: Int) {
        val comment = list[position]
        viewHolder.binding.comment = comment
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount() = list.size

    fun getItem(position: Int) = list[position]
}