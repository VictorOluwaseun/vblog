package com.decagon.vblog.repository

import com.decagon.vblog.model.api.RetrofitInstance
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post
import retrofit2.Response

class APIRepository: IRepository {
    override suspend fun getAllPosts(): Response<List<Post>> {
            return RetrofitInstance.api.getAllPosts()
    }

    override suspend fun getPostComments(postId: Int): Response<List<Comment>> {
            return RetrofitInstance.api.getPostComments(postId)
    }
}