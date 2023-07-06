package com.firstproject.usergithub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.firstproject.usergithub.network.ApiConfig
import com.firstproject.usergithub.R
import com.firstproject.usergithub.adapter.SectionsPagerAdapter
import com.firstproject.usergithub.databinding.ActivityDetailBinding
import com.firstproject.usergithub.model.UserResponse
import com.firstproject.usergithub.utils.loadImage
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var userName: String
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("userName").toString()
        userId = intent.getIntExtra("userId",0)

        binding.imgItemPhoto.loadImage("https://avatars.githubusercontent.com/u/${userId}")

        this.fetchUserDetail()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, userName)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun fetchUserDetail() {
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
                        binding.follower.text = buildString {
                            append("Followers :")
                            append(responseBody.followers)
                        }
                        binding.following.text = buildString {
                            append("Following :")
                            append(responseBody.following)
                        }
                        binding.login.text = responseBody.login
                        binding.tvItemName2.text = responseBody.name

                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
