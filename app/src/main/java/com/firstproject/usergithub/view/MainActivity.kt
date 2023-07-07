package com.firstproject.usergithub.view


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstproject.usergithub.network.ApiConfig
import com.firstproject.usergithub.R
import com.firstproject.usergithub.adapter.UserAdapter
import com.firstproject.usergithub.databinding.ActivityMainBinding
import com.firstproject.usergithub.model.UserList
import com.firstproject.usergithub.model.UserResponse
import com.firstproject.usergithub.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserList.addItemDecoration(itemDecoration)

        viewModel.loadingVisibility.observe(this) { visibility ->
            binding.progressBar.visibility = visibility
        }

        viewModel.userData.observe(this) { userList ->
            setUserData(userList)
        }

        viewModel.fetchUserData()
    }

//    private fun listUser() {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getUser("a")
//        client.enqueue(object : Callback<UserList> {
//            override fun onResponse(
//                call: Call<UserList>,
//                response: Response<UserList>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setUserData(responseBody.items)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<UserList>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }

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
        binding.rvUserList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
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
                                setUserData(responseBody.items)
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserList>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

}


