package com.firstproject.usergithub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.firstproject.usergithub.view.FollowersFollowingsFragment
//import com.firstproject.usergithub.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: String) : FragmentStateAdapter(activity) {
//    override fun createFragment(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> {
//                fragment = FollowingFragment()
//                val bundle = Bundle()
//                bundle.putString("login", login)
//                fragment.arguments = bundle
//            }
//            1 -> {
//                fragment = FollowersFragment()
//                val bundle = Bundle()
//                bundle.putString("login", login)
//                fragment.arguments = bundle
//            }
//        }
//        return fragment as Fragment
//    }


    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFollowingsFragment()
        fragment.arguments = Bundle().apply {
            putInt("login position", position + 1)
            putString("login", login)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}