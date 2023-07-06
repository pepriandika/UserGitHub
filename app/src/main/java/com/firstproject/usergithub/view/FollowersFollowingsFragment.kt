package com.firstproject.usergithub.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstproject.usergithub.network.ApiConfig
import com.firstproject.usergithub.adapter.UserAdapter
import com.firstproject.usergithub.databinding.FragmentFollowersBinding
import com.firstproject.usergithub.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFollowingsFragment : Fragment() {
    private lateinit var login: String
    private var position: Int = 0

    private lateinit var binding: FragmentFollowersBinding

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
            userList(login, position)
        }
    }

    private fun userList(login:String, pos: Int) {
        showLoading(true)
        val client = if (pos == 1) ApiConfig.getApiService()
            .getFollowing(login) else ApiConfig.getApiService().getFollowers(login)
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                showLoading(false)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) = binding.progressBar.isVisible == isLoading

    private fun setUserData(userResponse: List<UserResponse>) {
        val userList = ArrayList<UserResponse>()
        for (i in userResponse) {
            val user = UserResponse(
                i.followingUrl,
                i.login,
                i.id,
                i.followersUrl,
                i.avatarUrl,
                i.followers,
                i.following,
                i.name
            )
            userList.add(user)
        }
        val adapter = UserAdapter(userList)
        binding.rvUserListFollowers.adapter = adapter
    }
}