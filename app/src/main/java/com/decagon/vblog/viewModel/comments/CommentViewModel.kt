package com.decagon.vblog.viewModel.comments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.vblog.model.db.PostsDatabase
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentViewModel(postId: Int, context: Context): ViewModel(){
        // Room Repository
    private val roomRepository: RoomRepository

    init {
        // Post Dao
        val postDao = PostsDatabase.getDatabase(context).postDao()
        roomRepository = RoomRepository(postDao) // Instance of RoomRepo
    }


    var id: MutableLiveData<Int> = MutableLiveData()
    val allRoomCommentSize: LiveData<Int>
        get() = id
    // Get last comments Id
    fun getCommentsId(){
        viewModelScope.launch(Dispatchers.IO) {
            val lastId = roomRepository.getCommentLastId()
            withContext(Main){
                id.value = lastId
            }
        }
    }

    // Create Comment
    fun createComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.createComment(comment)
        }
    }
}