package com.decagon.vblog.model.shema

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "post_table")
data class Post (
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
): Serializable
