package com.example.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.database.FavoriteDataModel
import com.example.githubuser.databinding.UserAdapterBinding
import com.example.githubuser.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewModel>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val listFavorites = ArrayList<FavoriteDataModel>()
    fun setListFavorite(listFavorites: List<FavoriteDataModel>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding = UserAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val favorite = listFavorites[position]
        holder.bind(favorite)

        holder.itemView.setOnClickListener { onItemClickCallback.onClicked(favorite) }
        holder.itemView.setOnLongClickListener {
            onItemClickCallback.onLongClicked(favorite)
            true
        }
    }

    override fun getItemCount(): Int = listFavorites.size

    class ViewModel(private val binding: UserAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteDataModel) {
            with(binding) {
                tvUsername.text = favorite.login
                tvId.text = favorite.id.toString()

                Glide.with(itemView.context)
                        .load(favorite.avatarUrl)
                        .apply(RequestOptions().override(50, 50))
                        .into(imageItem)
            }
        }

    }

    interface OnItemClickCallback {
        fun onClicked(favorite: FavoriteDataModel)
        fun onLongClicked(favorite: FavoriteDataModel)
    }
}