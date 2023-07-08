package com.firstproject.usergithub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstproject.usergithub.adapter.UserAdapter
import com.firstproject.usergithub.databinding.FragmentFollowersBinding
import com.firstproject.usergithub.viewmodel.FollowersFollowingViewModel

class FollowersFollowingsFragment : Fragment() {
    private lateinit var login: String
    private var position: Int = 0

    private lateinit var binding: FragmentFollowersBinding
    private val viewModel: FollowersFollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserListFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUserListFollowers.addItemDecoration(itemDecoration)

        arguments?.let {
            login = it.getString("login").toString()
            position = it.getInt("login position")
        }

        val userAdapter = UserAdapter()
        binding.rvUserListFollowers.adapter = userAdapter

        viewModel.userData.observe(viewLifecycleOwner) { userList ->
            userAdapter.submitList(userList)
        }

        viewModel.loadingVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.progressBar.visibility = visibility
        }

        viewModel.fetchUserData(login, position)
    }

}