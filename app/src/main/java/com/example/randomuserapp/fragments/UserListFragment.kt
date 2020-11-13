package com.example.randomuserapp.fragments

import User
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.randomuserapp.R
import com.example.randomuserapp.UserListAdapter
import com.example.randomuserapp.databinding.UserListFragmentBinding
import com.example.randomuserapp.enums.DataState
import com.example.randomuserapp.view_model.UserListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModel()

    private lateinit var binding: UserListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UserListFragmentBinding.inflate(inflater)
        binding.rvUserList.adapter = UserListAdapter(emptyList()) {
            viewModel.onUserClicked(it)
        }
        binding.refreshButton.setOnClickListener {
            viewModel.onRefresh()
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            when(state!!) {
                DataState.LOADING -> { showLoading() }
                DataState.SUCCESS -> { showList() }
                DataState.ERROR -> { showError() }
            }
        }

        viewModel.usersLiveData.observe(viewLifecycleOwner) { users ->
            (binding.rvUserList.adapter as UserListAdapter).setList(users)
        }

        viewModel.navigationToUserLiveData.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                navigateToUserScreen(it)
            }
        }
    }

    private fun showLoading() {
        binding.swipeRefresh.isRefreshing = true
        binding.rvUserList.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.internetProblem.visibility = View.GONE
        binding.refreshButton.visibility = View.GONE
    }

    private fun showList() {
        binding.swipeRefresh.isRefreshing = false
        binding.rvUserList.visibility = View.VISIBLE
        binding.errorText.visibility = View.GONE
        binding.internetProblem.visibility = View.GONE
        binding.refreshButton.visibility = View.GONE
    }

    private fun showError() {
        binding.swipeRefresh.isRefreshing = false
        binding.rvUserList.visibility = View.GONE
        binding.errorText.visibility = View.VISIBLE
        binding.internetProblem.visibility = View.VISIBLE
        binding.refreshButton.visibility = View.VISIBLE
    }

    private fun navigateToUserScreen(user: User) {
        val action = UserListFragmentDirections.actionUserListFragmentToUserFragment(user)
        findNavController().navigate(action)
    }
}