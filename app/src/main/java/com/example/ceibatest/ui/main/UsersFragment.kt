package com.example.ceibatest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.ceibatest.R
import com.example.ceibatest.databinding.FragmentUsersBinding
import com.example.ceibatest.ui.main.viewmodel.UsersViewModel
import com.example.ceibatest.utils.BaseFragment
import com.example.ceibatest.utils.Commons
import com.example.ceibatest.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UsersFragment : BaseFragment(R.layout.fragment_users) {

    private lateinit var binding : FragmentUsersBinding
    private lateinit var viewModel : UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[UsersViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        checkSize()
    }

    private fun setupClickObserver(){
        viewModel.actionDetailsUser.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { action ->
                if (action){
                    viewModel.userDetails.value.let { user ->
                        goToDetails(user?.id!!)
                    }
                }
            }
        }
    }

    private fun goToDetails(userId : Int){
        findNavController().navigate( UsersFragmentDirections.actionMainFragmentToPostsFragment(userId))
    }

    private fun subscribe(){
        viewModel.users.removeObservers(viewLifecycleOwner)
        viewModel.users.observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    setupClickObserver()
                    binding.progressBar.visibility = View.GONE
                    binding.rvResults.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.tvNothingFound.visibility= View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvResults.visibility = View.GONE
                    Commons.showSnackBar(Commons.getString(R.string.something_went_wrong),binding.rvResults)
                }
            }
        }
    }

    private fun checkSize(){
        viewModel.usersAdapter.listSize.observe(requireParentFragment().viewLifecycleOwner){
            when (it){
                -1 -> {
                    binding.tvNothingFound.visibility = View.GONE
                }
                0 -> {
                    binding.tvNothingFound.visibility = View.VISIBLE
                    binding.rvResults.visibility = View.GONE
                }
                else -> {
                    binding.tvNothingFound.visibility = View.GONE
                    binding.rvResults.visibility = View.VISIBLE
                }
            }
        }
    }
}