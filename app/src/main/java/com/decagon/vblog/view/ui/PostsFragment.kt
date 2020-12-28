package com.decagon.vblog.view.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.decagon.microposts.utils.RecyclerViewClickListener
import com.decagon.vblog.viewModel.posts.PostsViewModel
import com.decagon.vblog.R
import com.decagon.vblog.adapter.PostAdapter
import com.decagon.vblog.databinding.PostsFragmentBinding
import com.decagon.vblog.model.shema.Post
import com.decagon.vblog.viewModel.posts.PostsViewModelFactory

class PostsFragment : Fragment(), RecyclerViewClickListener {

    var arrayList = MutableLiveData<List<Post>>()
    val tempPost = MutableLiveData<List<Post>>()

    /* lateinit postsfragment and viewemodel*/
    private lateinit var binding: PostsFragmentBinding

    private lateinit var viewModel: PostsViewModel

    // Initialising PostAdapter
    val adapter = PostAdapter()

// Oncreate view callback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    // Inflate the view
        binding = DataBindingUtil.inflate(inflater, R.layout.posts_fragment, container, false)
    // initialising postsviewmodelfactory
        val postsViewModelFactory = PostsViewModelFactory(requireContext())

            // referencing the postsviewmodel
        viewModel = ViewModelProvider(this, postsViewModelFactory).get(PostsViewModel::class.java)



    // Observing the combinedata using mutablelive data
        viewModel.combinedPostData()?.observe(viewLifecycleOwner, {
            it.let {
                val combinedPosts = it.second + it.first.reversed()
//                arrayList = combinedPosts as ArrayList<Post>
                adapter.postsData = combinedPosts
                tempPost.value = combinedPosts
                arrayList.value = combinedPosts
//                displayList.addAll(adapter.postsData)
            }
        })

    // Initialising the click listener
        adapter.listener = this
    // assigning the recyclerview adapter
        binding.rvPosts.adapter = adapter
    // The floating action button
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_postsFragment_to_addPostFragment)
        }
    // Allowing options menu
    setHasOptionsMenu(true)
        // Inflate view
        return binding.root
    }

    // Repopulate recycler after filtering
    private fun populateRecycler(){
        adapter.postsData = tempPost.value!!
    }


    // RecyclerViewClickListener Member
    override fun onRecyclerViewClickListener(view: View, post: Post) {
        // Transport data through directions
        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(post)
        // Navigate to the post details fragment
        view.findNavController().navigate(action)
    }

    // Override the onCreateOptionsMenu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        // Inflate the menu
        inflater.inflate(R.menu.main_menu, menu)
        // Getting the menuItem
        val menuItem = menu.findItem(R.id.actionSearch)
        // Check if menuItem is not null
        if (menuItem != null) {
            // Cast menuItem action as SearchView
            val searchView = menuItem.actionView as SearchView
            // Set on queryTextListener
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                // Returns true on queryTextSubmit
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                // Returns true on QueryTextChange and populate recycler every time
                override fun onQueryTextChange(newText: String?): Boolean {
                    var charValue: CharSequence = newText as CharSequence
                    tempPost.value = arrayList.value?.filter { it.title.contains(charValue, ignoreCase = true) }
                    populateRecycler()
                    return true
                }
            })
        }
    }
}