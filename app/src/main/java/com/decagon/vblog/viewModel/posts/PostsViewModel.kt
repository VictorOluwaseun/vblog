package com.decagon.vblog.viewModel.posts

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.vblog.model.db.PostsDatabase
import com.decagon.vblog.model.shema.Post
import com.decagon.vblog.repository.APIRepository
import com.decagon.vblog.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PostsViewModel(context: Context) : ViewModel() {

    private val _allPostsAPI: MutableLiveData<List<Post>> = MutableLiveData()
    private val allPostsAPI: LiveData<List<Post>>
        get() = _allPostsAPI

    private val allPostsFromRoom: LiveData<List<Post>>


    private var repository: RoomRepository

    init {
        // Post from API
        getAllPostsFromApi()
        // Post from Room
        //Post Dao
        val postDao = PostsDatabase.getDatabase(context).postDao()
        repository = RoomRepository(postDao)
        allPostsFromRoom = repository.readAllPosts
    }

    // Posts from API
    private fun getAllPostsFromApi(){
        val repository = APIRepository()
        viewModelScope.launch {
            try {
                if (repository.getAllPosts().isSuccessful){
                    val response = repository.getAllPosts().body()
                    _allPostsAPI.value = response
                }
            }catch (e: IOException){
                Log.i("Response", "No internet Connection") // Work on this later
            }
        }
    }

    // Add Post to Room
    fun createPost(post: Post){
        viewModelScope.launch(Dispatchers.IO) {
            repository.createPost(post)
        }
    }

    //Get Size of all posts in Room
    var id: MutableLiveData<Int> = MutableLiveData()
    val allRoomPostSize: LiveData<Int>
        get() = id


//    fun getAllPostsList(){
//        viewModelScope.launch(Dispatchers.IO) {
//            val posts = repository.getAllPost().size
//            withContext(Dispatchers.Main){
//                Log.i("Room comment size", posts.toString())
//                id.value = posts
//            }
//        }
//    }

    fun getPostId(){
//        var id: Long= 1;
        viewModelScope.launch(Dispatchers.IO) {
            val lastId = repository.getPostLastId()
            withContext(Main){
                Log.i("VM", lastId.toString())
                id.value = lastId
            }
        }
    }


    // Combine Live Data Source
    fun combinedPostData():CombinedPosts?{
        if (_allPostsAPI != null && allPostsFromRoom != null){
            return CombinedPosts(allPostsAPI, allPostsFromRoom)
        }
        return null
    }

}