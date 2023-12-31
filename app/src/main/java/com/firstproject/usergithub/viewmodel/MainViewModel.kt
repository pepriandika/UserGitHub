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

class MainViewModel: ViewModel() {
    private val _userData = MutableLiveData<List<UserResponse>>()
    private val _loadingVisibility = MutableLiveData<Int>()

    val userData: LiveData<List<UserResponse>> get() = _userData
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    var isDataFetched = false

    fun fetchUserData(query: String) {
        if (!isDataFetched) {
            showLoading(true)
            val client = ApiConfig.getApiService().getUser(query)
            client.enqueue(object : Callback<UserList> {
                override fun onResponse(
                    call: Call<UserList>,
                    response: Response<UserList>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _userData.value = responseBody.items
                            isDataFetched = true
                        }
                    }
                }

                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    showLoading(false)
                }
            })
        }
    }

    fun showLoading(isLoading: Boolean) {
        _loadingVisibility.value = if (isLoading) View.VISIBLE else View.GONE
    }
}