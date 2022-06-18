package com.fdl.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fdl.githubuser.databinding.ItemRowUserBinding

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val listUser = ArrayList<User>()
    fun setUsers(user: ArrayList<User>){
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }
    inner class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListUserAdapter.ListViewHolder, position: Int) {
        val data = listUser[position]
            Glide.with(holder.itemView.context)
                .load(data.photo)
                .apply(RequestOptions())
                .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemUsername.text = data.username
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(data)
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}
