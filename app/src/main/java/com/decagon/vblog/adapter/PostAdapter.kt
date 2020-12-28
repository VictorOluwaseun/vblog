package com.decagon.vblog.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.microposts.utils.RecyclerViewClickListener
import com.decagon.vblog.databinding.PostItemViewBinding
import com.decagon.vblog.model.shema.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    // The posts list
    var postsData = listOf<Post>()
        set(value) { //Backing property. Runs notifyDataSetChanged every time
            field = value
            notifyDataSetChanged()
        }

    // Instantiating the listener
    var listener: RecyclerViewClickListener? = null
    // The onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // Inflate the view
        val binding = PostItemViewBinding.inflate(layoutInflater, parent, false)
        // Return instance of postViewHolder
        return PostViewHolder(binding)
    }
    // Binding the view
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Each post
        val item = postsData[position]
        // initClick for handling click event
        holder.initClick(item, listener!!)
    }
    // Getting the posts size
    override fun getItemCount() = postsData.size
    // The PostViewHolder class
    inner class PostViewHolder(val binding: PostItemViewBinding): RecyclerView.ViewHolder(binding.root){
        //  The view and click handler
        fun initClick(data:Post, action:RecyclerViewClickListener){
            // Setting the texts
            binding.tvPostTitle.text  = data.title
            // The click event
            binding.LPost.setOnClickListener{
                action.onRecyclerViewClickListener(it, data)
            }
        }
    }

}