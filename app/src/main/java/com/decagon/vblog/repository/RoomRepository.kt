package com.decagon.vblog.repository

import androidx.lifecycle.LiveData
import com.decagon.vblog.model.db.PostDao
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post

class RoomRepository(private val postDao: PostDao): IRepository {

    val readAllPosts: LiveData<List<Post>> = postDao.readAllPosts()

    override suspend fun createPost(post:Post){
        postDao.createPost(post)
    }

//    suspend fun getReadAllPosts():List<Post>{
//        return postDao._readAllData()
//    }

    override suspend fun createComment(comment: Comment) {
        postDao.createComment(comment)
    }

    override fun readComments(postId: Int) = postDao.readComments(postId)


    override suspend fun getPostLastId():Int {
       return postDao.getPostLastId()
    }

    override suspend fun getCommentLastId(): Int {
        return postDao.getCommentLastId()
    }

}