package com.example.affirmation.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import com.airbnb.lottie.compose.*

class AffirmationViewModel : ViewModel() {
    var affirmation by mutableStateOf("")
       /* private set*/
    var isLoading by mutableStateOf(false)
       /* private set*/

    fun refreshAffirmation() {
        isLoading = true
        viewModelScope.launch {
            try {
                val queue = Volley.newRequestQueue(MyApplication.instance)
                val url = "https://www.affirmations.dev/"
                val stringRequest = StringRequest(
                    com.android.volley.Request.Method.GET, url,
                    { response ->
                        val jsonObject = JSONObject(response)
                        affirmation = jsonObject.getString("affirmation")
                        isLoading = false
                        Log.d(TAG, "Error: $jsonObject")
                    },
                    { error ->
                        isLoading = false
                        Log.d(TAG, "Error: $error")
                        // handle error
                    })
                queue.add(stringRequest)
            } catch (e: Exception) {
                isLoading = false
                // handle exception
            }
        }
    }
}

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
