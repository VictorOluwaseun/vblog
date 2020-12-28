package com.decagon.vblog.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post

const val DATABASE_VERSION = 1
@Database(
    entities = [Post::class, Comment::class],
    version = DATABASE_VERSION
)
abstract class PostsDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object{
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getDatabase(context: Context): PostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    "posts_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}