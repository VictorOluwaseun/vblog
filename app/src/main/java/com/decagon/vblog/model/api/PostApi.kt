package com.decagon.vblog.model.api

import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("/posts")
    suspend fun getAllPosts():Response<List<Post>>

    @GET("/posts/{id}/comments")
    suspend fun getPostComments(
        @Path("id") id: Int
    ) : Response<List<Comment>>

}