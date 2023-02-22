package com.example.affirmation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.affirmation.viewmodel.AffirmationViewModel
import com.wahid.affirmationquotes.R

@Composable
fun Affirmation(viewModel: AffirmationViewModel) {
    Column {
        Text(
            text = "Affirmation of the day",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = { viewModel.refreshAffirmation() }
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
        }

        if (viewModel.isLoading) {

            Box(modifier = Modifier.fillMaxSize()) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading),)
                val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier.size(100.dp)
                )
            }
        } else {
            Text(
                text = viewModel.affirmation,
                style = MaterialTheme.typography.body1,
            )
        }
        Text(
            text = viewModel.affirmation,
            style = MaterialTheme.typography.body1,
        )



    }
}

