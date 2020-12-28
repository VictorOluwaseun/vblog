package com.decagon.vblog.viewModel.posts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.vblog.model.db.PostsDatabase
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.model.shema.Post
import com.decagon.vblog.repository.APIRepository
import com.decagon.vblog.repository.RoomRepository
import com.decagon.vblog.viewModel.comments.CombinedComments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostDetailsViewModel(post: Post, context: Context) : ViewModel() {

    private val aPIRepository = APIRepository()

    private val commentsFromAPI: MutableLiveData<List<Comment>> = MutableLiveData()

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _body = MutableLiveData<String>()
    val body: LiveData<String>
        get() = _body

private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name



    private val commentsFromRoom: LiveData<List<Comment>>


    private val roomRepository: RoomRepository

    init {
        _title.value = post.title
        _body.value = post.body
        _name.value = "John Doe"

        getCommentsFromAPI(post.id.toInt())

        val postDao = PostsDatabase.getDatabase(context).postDao()
        roomRepository = RoomRepository(postDao)

        commentsFromRoom = roomRepository.readComments(post.id.toInt())
    }

    private fun getCommentsFromAPI(postId: Int) {
        viewModelScope.launch {
            if (aPIRepository.getPostComments(postId).isSuccessful){
                val response = aPIRepository.getPostComments(postId).body()
                commentsFromAPI.value = response
            }
        }
    }


    fun combinedCommentsData(): CombinedComments?{
        return CombinedComments(commentsFromAPI, commentsFromRoom)
    }

}