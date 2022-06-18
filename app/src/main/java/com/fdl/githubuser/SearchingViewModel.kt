package com.fdl.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchingViewModel:ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchingUser(username: String) {
        RetrofitClient.instance.getSearchUser(username).enqueue(object  : Callback<ResponUser>{
            override fun onResponse(call: Call<ResponUser>, response: Response<ResponUser>) {
                if (response.isSuccessful){
                    listUser.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<ResponUser>, t: Throwable) {
                t.message?.let { Log.d("gagal memuat data", it) }
            }

        })
    }

    fun getSerchingUser(): LiveData<ArrayList<User>>{
        return listUser
    }

}