package com.decagon.vblog.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decagon.vblog.R
import com.decagon.vblog.databinding.AddPostFragmentBinding
import com.decagon.vblog.model.shema.Post
import com.decagon.vblog.viewModel.posts.PostsViewModelFactory
import com.decagon.vblog.utils.Validation
import com.decagon.vblog.viewModel.posts.PostsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AddPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPostFragment : Fragment() {
    // Promising AddPostFragmentBinding
    lateinit var binding: AddPostFragmentBinding
    // The instance of Validation class
    private val validation by lazy {
        Validation()
    }
    // Promising PostsViewModel
    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.add_post_fragment, container, false)
        // PostsViewModel Factory to pass context
        val postsViewModelFactory = PostsViewModelFactory(requireContext())
        // Referencing PostsViewModel
        viewModel = ViewModelProvider(this, postsViewModelFactory).get(PostsViewModel::class.java)
        // Submit Post action
        binding.btnSubmitPost.setOnClickListener {
            viewModel.getPostId()
        }

        // Observing allRoomPostSize
        viewModel.allRoomPostSize.observe(viewLifecycleOwner,
            {
                // Setting post Id
                val id = 101 + it
                // Create post
                onCreatePost(id)
            })

        // Cancel Button action
        binding.btnCancelPost.setOnClickListener {
            findNavController().navigate(R.id.action_addPostFragment_to_postsFragment)
        }
        // Return view
        return binding.root
    }

    // Create Post method
    private fun onCreatePost(postId: Int) {
        val body = binding.edAddPostBody.text.toString()
        val title = binding.btnAddPostTitle.text.toString()
        if (validation.inputCheck(title, body)) {
            // Instance of Post
            val post = Post(id = postId, body = body, title = title, userId = 101)
            // Create post
            viewModel.createPost(post)
            // Navigate to postsFragment
            findNavController().navigate(R.id.action_addPostFragment_to_postsFragment)
        } else {
            // If any of the field is empty
            Toast.makeText(requireContext(), "Please fill out all field.", Toast.LENGTH_LONG).show()
        }
    }
}


