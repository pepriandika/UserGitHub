package com.firstproject.usergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.firstproject.usergithub.view.DetailActivity
import com.firstproject.usergithub.databinding.UserCardViewBinding
import com.firstproject.usergithub.model.UserResponse

class UserAdapter(private val listUser: List<UserResponse>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
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
        Glide.with(holder.itemView.context)
            .load("https://avatars.githubusercontent.com/u/${user.id}")
            .transform(CircleCrop())
            .into(holder.imgPhoto)
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