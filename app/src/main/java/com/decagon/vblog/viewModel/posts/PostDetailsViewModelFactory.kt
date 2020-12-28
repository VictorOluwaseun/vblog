package com.decagon.vblog.viewModel.posts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.vblog.model.shema.Post

class PostDetailsViewModelFactory(private val post: Post, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailsViewModel(post, context) as T
    }
}