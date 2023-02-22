package com.wahid.affirmationquotes.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

@Composable
fun Affirmation() {
    val context = LocalContext.current

    var affirmation by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = Unit) {
        // membuat request ke server API
        val queue = Volley.newRequestQueue(context)
        val url = "https://www.affirmations.dev/"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                affirmation = jsonObject.getString("affirmation")
                Log.d(ContentValues.TAG, "Error: $jsonObject")
            },
            { error ->
                Log.d(ContentValues.TAG, "Error: $error")
            })
        queue.add(stringRequest)
    }

    Column() {
        Text(
            text = "Affirmation of the day",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {
                isLoading = true
                val queue = Volley.newRequestQueue(context)
                val url = "https://www.affirmations.dev/"

                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        val jsonObject = JSONObject(response)
                        affirmation = jsonObject.getString("affirmation")
                        isLoading = false
                    },
                    { error ->
                        Log.d(ContentValues.TAG, "Error: $error")
                        isLoading = false
                    })

                queue.add(stringRequest)
            }
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
        }

        Text(
            text = affirmation,
            style = MaterialTheme.typography.body1,
        )

    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R),)
            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
