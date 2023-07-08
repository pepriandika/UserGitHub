package com.firstproject.usergithub.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstproject.usergithub.model.UserList
import com.firstproject.usergithub.model.UserResponse
import com.firstproject.usergithub.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _userDetail = MutableLiveData<UserResponse>()
    val userDetail: LiveData<UserResponse> get() = _userDetail

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    var isDataFetched = false

    fun fetchUserDetail(userName: String) {
        if (!isDataFetched) {
            showLoading(true)
            val client = ApiConfig.getApiService().getDetailUser(userName)
            client.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _userDetail.value = responseBody
                            isDataFetched = true
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    showLoading(false)
                }
            })
        }
    }

    fun showLoading(isLoading: Boolean) {
        _loadingVisibility.value = if (isLoading) View.VISIBLE else View.GONE
    }
}