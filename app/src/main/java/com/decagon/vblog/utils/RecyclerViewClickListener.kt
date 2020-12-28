package com.decagon.microposts.utils

import android.view.View
import com.decagon.vblog.model.shema.Post

interface RecyclerViewClickListener {
    fun onRecyclerViewClickListener(view: View, post: Post)
}