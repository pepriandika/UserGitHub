package com.firstproject.usergithub.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstproject.usergithub.adapter.UserAdapter
import com.firstproject.usergithub.model.UserList
import com.firstproject.usergithub.model.UserResponse
import com.firstproject.usergithub.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFollowingViewModel: ViewModel() {
    private val _userData = MutableLiveData<List<UserResponse>>()
    private val _loadingVisibility = MutableLiveData<Int>()

    val userData: LiveData<List<UserResponse>> get() = _userData
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    var isDataFetched = false

    fun fetchUserData(userName: String, pos: Int) {
        if (!isDataFetched) {
            showLoading(true)
            val client = if (pos == 1) {
                ApiConfig.getApiService().getFollowing(userName)
            } else {
                ApiConfig.getApiService().getFollowers(userName)
            }
            client.enqueue(object : Callback<List<UserResponse>> {
                override fun onResponse(
                    call: Call<List<UserResponse>>,
                    response: Response<List<UserResponse>>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _userData.value = responseBody
                            isDataFetched = true
                        }
                    }
                }

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    showLoading(false)
                }
            })
        }
    }

    fun showLoading(isLoading: Boolean) {
        _loadingVisibility.value = if (isLoading) View.VISIBLE else View.GONE
    }
}