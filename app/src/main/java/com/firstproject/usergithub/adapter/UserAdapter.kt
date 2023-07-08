package com.firstproject.usergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firstproject.usergithub.view.DetailActivity
import com.firstproject.usergithub.databinding.UserCardViewBinding
import com.firstproject.usergithub.model.UserResponse
import com.firstproject.usergithub.utils.loadImage

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var listUser: List<UserResponse> = emptyList()

    fun submitList(userList: List<UserResponse>) {
        listUser = userList
        notifyDataSetChanged()
    }

    class ViewHolder(binding: UserCardViewBinding): RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItemName
        val imgPhoto = binding.imgItemPhoto
        val detailButton = binding.materialButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvItem.text = user.login

        holder.imgPhoto.loadImage("https://avatars.githubusercontent.com/u/${user.id}")
        holder.detailButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("userName", user.login)
            intent.putExtra("userId", user.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}