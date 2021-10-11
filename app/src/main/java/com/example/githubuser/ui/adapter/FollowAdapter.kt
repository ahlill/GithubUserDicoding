package com.example.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.api.ItemsItem
import com.example.githubuser.databinding.UserAdapterBinding

class FollowAdapter(private val dataUsers: List<ItemsItem>?) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            UserAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = dataUsers?.get(position)
        holder.bind(user)
    }

    override fun getItemCount(): Int = dataUsers?.size ?: 0

    class ViewHolder(private val binding: UserAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        internal fun bind(data: ItemsItem?) = with(binding) {

            Glide.with(itemView.context)
                    .load(data?.avatarUrl)
                    .apply(RequestOptions().override(50, 50))
                    .into(imageItem)

            tvUsername.text = data?.login
            tvId.text = data?.id.toString()

        }
    }
}