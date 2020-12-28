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
import androidx.navigation.fragment.navArgs
import com.decagon.vblog.R
import com.decagon.vblog.databinding.AddCommentFragmentBinding
import com.decagon.vblog.model.shema.Comment
import com.decagon.vblog.utils.Validation
import com.decagon.vblog.viewModel.comments.CommentViewModel
import com.decagon.vblog.viewModel.comments.CommentViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [AddCommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCommentFragment : Fragment() {
    // Get args
    private val args: AddCommentFragmentArgs by navArgs()
    // Promise to use binding
    lateinit var binding: AddCommentFragmentBinding
    // promise to use viewModel
    lateinit var viewModel: CommentViewModel
    // Get validation
    private val validation by lazy {
        Validation()
    }

    // onCreateView callback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.add_comment_fragment, container, false)
        // Get post id from args
        val (postId) = args
        // Instantiating CommentViewModelFactory
        val viewModelFactory= CommentViewModelFactory(postId, requireContext())
        // Getting an instance of ViewModel using ViewModelProvider
       viewModel = ViewModelProvider(this, viewModelFactory).get(CommentViewModel::class.java)
        // The cancel button
        binding.btnCancelComment.setOnClickListener{
            findNavController().popBackStack()
        }
        // Observing and get last post id from room
        viewModel.allRoomCommentSize.observe(viewLifecycleOwner, {
            val body = binding.edBody.text.toString()
            // Validate body field
            if (validation.inputCheck(body)){
                // Adding comment id
                val idComment = it+501
                // Instance of comment
                val comment = Comment(id = idComment, postId = postId, body = body, name = "John Doe", email= "johndoe@gmail.com" )
                // Create comment
                viewModel.createComment(comment)
                // Done creating comment and go back to previous screen
                findNavController().popBackStack()
            }else{
                // If the body field is blank
                Toast.makeText(requireContext(), "Comment can not be blank", Toast.LENGTH_LONG).show()
            }

        })
        // The add comment button
        binding.btnAddComment.setOnClickListener{
            // Get comments Id
                viewModel.getCommentsId()
        }
        // Return inflater
        return binding.root
    }
}