package tv.cloudwalker.lunalauncher.com.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import tv.cloudwalker.lunalauncher.com.ApiState
import tv.cloudwalker.lunalauncher.com.viewModel.CatalogViewModel


@Composable
fun MainScreen() {
    val catalogViewModel: CatalogViewModel = viewModel()
    val TAG = "MainScreen"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when (val catalogResult = catalogViewModel.catalogResponse.value) {
            is ApiState.Loading -> {
                Log.d(TAG, "inside loading")
                Log.d(TAG, "MainScreen: inside box circular ")
                CircularProgressIndicator(
                    modifier = Modifier.wrapContentSize(),
                    color = Color.Blue
                )
            }

            is ApiState.Success -> {
                Log.d(TAG, "MainScreen: " + catalogResult.catalog.body())
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = catalogResult.catalog.body().toString(),
                    fontStyle = FontStyle.Normal,
                    color = Color.White
                )

            }

            is ApiState.Failure -> {
                Log.d(TAG, "MainScreen" + catalogResult.msg)
            }

            is ApiState.Empty -> {
                Log.d(TAG, "MainScreen: is empty")
            }

        }
    }


}