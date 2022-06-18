package com.fdl.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fdl.githubuser.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchingViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterUser: ListUserAdapter
    private lateinit var themeViewModel: ThemeViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvUser.setHasFixedSize(true)
        adapterUser = ListUserAdapter()
        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchingViewModel::class.java)
        searchViewModel.getSerchingUser().observe(this) {
            if (it != null) {
                showLoading(false)
                adapterUser.setUsers(it)
            }
        }
        showRecyclerList()

    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapterUser


            adapterUser.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    val intentToDetail = Intent(this@MainActivity, DetailUser::class.java)
                    intentToDetail.putExtra(DetailUser.ID_USER, user.id)
                    intentToDetail.putExtra(DetailUser.USERNAME_USER, user.username)
                    intentToDetail.putExtra(DetailUser.AVATAR_USER, user.photo)
                    startActivity(intentToDetail)
                }
            })
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                searchViewModel.setSearchingUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) return true
                searchViewModel.setSearchingUser(newText)
                showLoading(true)
                return true
            }


        })

        val themeSwitchButton = menu?.findItem(R.id.theme_switch_button)?.actionView as SwitchMaterial
        val pref = SettingsPreferenceAppTheme.newInstance(dataStore)
        themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(ThemeViewModel::class.java)
        themeViewModel.getThemeMode().observe(this, { isNightModeActive: Boolean ->
            if (isNightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeSwitchButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeSwitchButton.isChecked = false
            }
        })

        themeSwitchButton.setOnCheckedChangeListener { _, isChecked ->
            themeViewModel.setThemeMode(isChecked)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favoriteUser -> {
                val intentToFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intentToFavorite)
                return true
            } else -> return true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}