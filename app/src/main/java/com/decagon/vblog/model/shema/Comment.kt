package com.decagon.vblog.model.shema

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class Comment(
    @PrimaryKey
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)