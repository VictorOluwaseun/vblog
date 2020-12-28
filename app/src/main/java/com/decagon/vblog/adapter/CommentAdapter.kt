package com.decagon.vblog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.vblog.databinding.CommentItemViewBinding
import com.decagon.vblog.model.shema.Comment


class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var comments = listOf<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentItemViewBinding.inflate(layoutInflater, parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = comments[position]
        holder.binding.tvCommentBody.text = item.body

//        holder.binding
    }

    override fun getItemCount() = comments.size

    inner class CommentViewHolder(val binding: CommentItemViewBinding):RecyclerView.ViewHolder(binding.root)

}
