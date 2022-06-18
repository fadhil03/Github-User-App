package com.fdl.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    val listUser = MutableLiveData<User>()
    val listUserFollowers = MutableLiveData<ArrayList<User>>()
    val listUserFollowing = MutableLiveData<ArrayList<User>>()

    fun setUser(username:String){
        RetrofitClient.instance.getDetailUser(username).enqueue(object  : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    listUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message?.let { Log.d("gagal memuat data", it) }
            }

        })
    }
    fun setUserFollowers(username: String) {
        RetrofitClient.instance.getFollowers(username).enqueue(object  : Callback<ArrayList<User>>{
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful){
                    listUserFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                t.message?.let { Log.d("gagal memuat data", it) }
            }

        })
    }
    fun setUserFollowing(username: String) {
        RetrofitClient.instance.getFollowing(username).enqueue(object  : Callback<ArrayList<User>>{
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful){
                    listUserFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                t.message?.let { Log.d("gagal memuat data", it) }
            }

        })
    }

    fun getDetailUser(): MutableLiveData<User> {
        return listUser
    }
    fun getUserFollowers(): LiveData<ArrayList<User>> {
        Log.d("List User :",listUserFollowers.toString())
        return listUserFollowers
    }

    fun getUserFollowing(): LiveData<ArrayList<User>> {
        return listUserFollowing
    }

}




