package com.decagon.vblog.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagon.vblog.R
import com.decagon.vblog.adapter.CommentAdapter
import com.decagon.vblog.databinding.PostDetailsFragmentBinding
import com.decagon.vblog.viewModel.posts.PostDetailsViewModelFactory
import com.decagon.vblog.viewModel.posts.PostDetailsViewModel

class PostDetailsFragment : Fragment() {
    // Get the passed the args
    private val args: PostDetailsFragmentArgs by navArgs()
    // Promise to use binding and viewModel
    private lateinit var binding: PostDetailsFragmentBinding
    private lateinit var viewModel: PostDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the view
        binding = DataBindingUtil.inflate(inflater, R.layout.post_details_fragment, container, false)
        // Get post from args
        val post = args.post
        // Instance of CommentAdapter
        val adapter = CommentAdapter()
        // Pass the post and Context
        val viewModelFactory = PostDetailsViewModelFactory(post, requireContext())
        // Referencing PostDetailsViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostDetailsViewModel::class.java)

        // Observing combinedCommentsData
        viewModel.combinedCommentsData()?.observe(viewLifecycleOwner, {
            it.let {
                val combinedComments = it.second + it.first
                adapter.comments = combinedComments
            }
        })

        // setting PostViewModel from xml
        binding.postViewModel = viewModel

        // Click Listener on add comment button
        binding.btnAddComment.setOnClickListener{
            val action = PostDetailsFragmentDirections.actionPostDetailsFragmentToAddCommentFragment(post.id)
            findNavController().navigate(action)
        }

        binding.rvComment.adapter = adapter

        return binding.root
    }
}