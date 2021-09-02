package com.example.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.DetailActivity.Companion.DATA_EXTRA
import com.example.githubuser.databinding.UserAdapterBinding

class UserAdapter(private val dataUser: DataModel): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {

        val dataUser = dataUser?.users?.get(position)
        holder.bind(dataUser)

        holder.binding.rvItemCardview.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DATA_EXTRA, dataUser)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dataUser.users?.size ?: 0

    inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = UserAdapterBinding.bind(view)

        internal fun bind(data: UsersItem?) = with(binding){
            val potoInt = itemView.resources.getIdentifier(data?.avatar, null, itemView.context.packageName)
            gambarItem.setImageResource(potoInt)
            tvName.text = data?.name
            tvUsername.text = data?.username
        }
    }
}