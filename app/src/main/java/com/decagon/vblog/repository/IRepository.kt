package com.decagon.vblog.repository

import androidx.lifecycle.LiveData
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post
import retrofit2.Response

interface IRepository {
// Get all posts from api
    suspend fun getAllPosts():Response<List<Post>>{
            TODO("Not yet implemented")
}

    suspend fun createPost(post:Post){
        TODO("Not yet implemented")
    }

    suspend fun createComment(comment:Comment){
        TODO("Not yet implemented")
    }

     fun readComments(postId:Int):LiveData<List<Comment>>{
        TODO("Not yet implemented")
    }

    suspend fun getPostComments(postId: Int): Response<List<Comment>> {
        TODO()
    }

    suspend fun getPostLastId(): Int{
        TODO()
    }

    suspend fun getCommentLastId():Int{
        TODO()
    }

}