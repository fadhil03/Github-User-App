package com.fdl.githubuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FollowerFragment : Fragment() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var rvFollowers: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(USERNAME_USER)
        val index = arguments?.getInt(SECTION_NUMBER, 0)
        Log.d("username", username.toString())

        rvFollowers = view.findViewById(R.id.rvFollowers)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()


        if (index==0) {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
            if (username != null) {
                showLoading(true)
                viewModel.setUserFollowers(username)
            }
            viewModel.getUserFollowers().observe(viewLifecycleOwner, {
                if (it != null) {
                    adapter.setUsers(it)
                }
                showListFollow()
                showLoading(false)
            })
        } else {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
            if (username != null) {
                showLoading(true)
                viewModel.setUserFollowing(username)
            }
            viewModel.getUserFollowing().observe(viewLifecycleOwner, {
                if (it != null) {
                    adapter.setUsers(it)
                }
                showListFollow()
                showLoading(false)
            })
        }
    }

    private fun showListFollow() {
        rvFollowers.setHasFixedSize(true)
        rvFollowers.layoutManager = LinearLayoutManager(activity)
        rvFollowers.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(activity, DetailUser::class.java)
                intentToDetail.putExtra(DetailUser.USERNAME_USER, data.username)
                startActivity(intentToDetail)
            }
        })
    }

    companion object {
        const val SECTION_NUMBER = "section_number"
        private const val USERNAME_USER = "username"
        fun newInstance(index: Int, username: String) = FollowerFragment().apply {
            arguments = Bundle().apply {
                putInt(SECTION_NUMBER, index)
                putString(USERNAME_USER, username)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar = view?.findViewById(R.id.progressBar)!!
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}