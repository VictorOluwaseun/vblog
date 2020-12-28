package com.decagon.vblog.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createPost(post: Post)

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun readAllPosts(): LiveData<List<Post>>

    //    Comment Dao
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createComment(comment: Comment)

    @Query("SELECT * FROM comment_table WHERE postId = :id ORDER BY id DESC")
    fun readComments(id: Int): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table ORDER BY id ASC")
    fun readAllComments(): LiveData<List<Comment>>

    @Query("SELECT COUNT(*) FROM post_table")
    fun getPostLastId(): Int

    @Query("SELECT COUNT(*) FROM comment_table")
    fun getCommentLastId(): Int
}