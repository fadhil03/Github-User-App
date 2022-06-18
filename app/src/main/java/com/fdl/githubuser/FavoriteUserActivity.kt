package com.fdl.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fdl.githubuser.database.FavoriteUser
import com.fdl.githubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var adapter: ListFavoriteUserAdapter
    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Favorite User"
        actionBar?.setDisplayHomeAsUpEnabled(true)


        adapter = ListFavoriteUserAdapter()
        adapter.notifyDataSetChanged()

        val favViewModel = favViewModel(this)
        favViewModel.getAllFavorite().observe(this, {
            if (it != null) {
                adapter.setListFavorite(it)
                showListFavorite()
            }
        })
    }

    private fun showListFavorite() {
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.setHasFixedSize(true)
        binding.rvFavoriteUser.adapter = adapter
        adapter.setOnItemClickData(object : ListFavoriteUserAdapter.OnItemClickClickCallback {
            override fun onItemClicked(user: FavoriteUser) {
                val intentToDetail = Intent(this@FavoriteUserActivity, DetailUser::class.java)
                intentToDetail.putExtra(DetailUser.ID_USER, user.id)
                intentToDetail.putExtra(DetailUser.USERNAME_USER, user.login)
                intentToDetail.putExtra(DetailUser.AVATAR_USER, user.avatar_url)
                startActivity(intentToDetail)
            }
        })
    }

    private fun favViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factoryViewModel = FavoriteViewModelFactory.newInstance(activity.application)
        return ViewModelProvider(activity, factoryViewModel)[FavoriteUserViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}