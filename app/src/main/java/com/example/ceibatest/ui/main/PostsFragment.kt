package com.example.ceibatest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.ceibatest.R
import com.example.ceibatest.databinding.FragmentPostsBinding
import com.example.ceibatest.ui.main.viewmodel.PostsViewModel
import com.example.ceibatest.utils.BaseFragment
import com.example.ceibatest.utils.Commons
import com.example.ceibatest.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsFragment : BaseFragment(R.layout.fragment_posts) {

    private lateinit var binding : FragmentPostsBinding
    private lateinit var viewModel : PostsViewModel
    private val args : PostsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[PostsViewModel::class.java]
        subscribe()
    }

    private fun subscribe(){
        viewModel.getPostsByUser(args.userId).observe(this){
            when (it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Commons.showSnackBar(Commons.getString(R.string.something_went_wrong),binding.rvResults)
                }
            }
        }
    }
}