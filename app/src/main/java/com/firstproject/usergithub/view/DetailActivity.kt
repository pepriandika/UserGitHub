package com.firstproject.usergithub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.firstproject.usergithub.R
import com.firstproject.usergithub.adapter.SectionsPagerAdapter
import com.firstproject.usergithub.databinding.ActivityDetailBinding
import com.firstproject.usergithub.utils.loadImage
import com.firstproject.usergithub.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    private lateinit var userName: String
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        userName = intent.getStringExtra("userName").toString()
        userId = intent.getIntExtra("userId",0)

        binding.imgItemPhoto.loadImage("https://avatars.githubusercontent.com/u/${userId}")

        val sectionsPagerAdapter = SectionsPagerAdapter(this, userName)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel.fetchUserDetail(userName)

        viewModel.loadingVisibility.observe(this) { visibility ->
            binding.progressBar2.visibility = visibility
        }

        viewModel.userDetail.observe(this) { userDetail ->
            binding.follower.text = buildString {
                            append("Followers :")
                            append(userDetail.followers)
                        }
                        binding.following.text = buildString {
                            append("Following :")
                            append(userDetail.following)
                        }
            binding.login.text = userDetail.login
            binding.tvItemName2.text = userDetail.name
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
