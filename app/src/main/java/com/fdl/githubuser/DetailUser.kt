package com.fdl.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fdl.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail_title)
        val id = intent.getIntExtra(ID_USER, 0)
        val username = intent.getStringExtra(USERNAME_USER).toString()
        val avatar = intent.getStringExtra(AVATAR_USER)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        showLoading(true)
        viewModel.setUser(username)
        viewModel.getDetailUser().observe(this) {
            if (it != null) {
                Glide.with(this@DetailUser)
                    .load(it.photo)
                    .into(binding.imgItemPhoto)
                binding.tvName.text = it.name
                binding.tvUsername.text = "username : " + it.username
                binding.tvRepository.text = it.repository + " repository"
                binding.tvCompany.text = it.company
                binding.tvLocation.text = it.location
                showLoading(false)
            }

        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,username)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.view.isClickable = true
            tab.text = resources.getString(DetailUser.TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val favViewModel = favViewModel(this)
        CoroutineScope(Dispatchers.IO).launch {
            val count = favViewModel.getUser(username)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0.toString()) {
                        binding.btnFavorite.isChecked = true
                        isFavorite = true
                    } else {
                        binding.btnFavorite.isChecked = false
                        isFavorite = false
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                favViewModel.addFavorite(id, username, avatar)
                Toast.makeText(this, username+" ditambahkan ke Favorit", Toast.LENGTH_SHORT).show()
            } else {
                favViewModel.deleteUser(id)
                Toast.makeText(this, username+" dihapus dari Favorit", Toast.LENGTH_SHORT).show()
            }
            binding.btnFavorite.isChecked = isFavorite
        }
    }

    private fun favViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factoryViewModel = FavoriteViewModelFactory.newInstance(activity.application)
        return ViewModelProvider(activity, factoryViewModel)[FavoriteUserViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ID_USER = "id"
        const val USERNAME_USER = "username"
        const val AVATAR_USER = "photo"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}