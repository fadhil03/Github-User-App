package com.fdl.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fdl.githubuser.database.FavoriteUser
import com.fdl.githubuser.databinding.ItemRowUserBinding

class ListFavoriteUserAdapter: RecyclerView.Adapter<ListFavoriteUserAdapter.ListViewHolder>() {
    private val listFavoriteUser = ArrayList<FavoriteUser>()

    fun setListFavorite(favUsers: List<FavoriteUser>) {
        val favDiffCallback = FavoriteUserDiffCallback(this.listFavoriteUser, listFavoriteUser)
        val favDiffResult = DiffUtil.calculateDiff(favDiffCallback)
        this.listFavoriteUser.clear()
        this.listFavoriteUser.addAll(favUsers)
        notifyDataSetChanged()
        favDiffResult.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavoriteUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listFavoriteUser[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar_url)
            .apply(RequestOptions())
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemUsername.text = data.login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(data)
        }

    }

    interface OnItemClickClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }

    private lateinit var onItemClickCallback: OnItemClickClickCallback
    fun setOnItemClickData(onItemClickData: OnItemClickClickCallback) {
        this.onItemClickCallback = onItemClickData
    }











}