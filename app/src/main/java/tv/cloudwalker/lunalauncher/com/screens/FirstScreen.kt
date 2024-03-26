package tv.cloudwalker.lunalauncher.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import tv.cloudwalker.lunalauncher.com.CarouselState
import tv.cloudwalker.lunalauncher.com.TileModelState
import tv.cloudwalker.lunalauncher.com.data.catsModel.Catalog
import tv.cloudwalker.lunalauncher.com.screens.common.FeaturedMoviesCarousel
import tv.cloudwalker.lunalauncher.com.utils.SkinResource
import tv.cloudwalker.lunalauncher.com.viewModel.CatalogViewModel
import tv.cloudwalker.lunalauncher.presentation.common.MoviesRow


@Composable
fun FirstScreen(
    catalog: Catalog,
    responseCode: Int,
    skinResource: SkinResource,
    catalogViewModel: CatalogViewModel,
) {

    LaunchedEffect(Unit) {
        catalogViewModel.getCarousel(catalog.pages[0].carouselEndpoint)
        catalogViewModel.getRow(catalog.pages[0].rowContentEndpoint)

    }
    val context = LocalContext.current

    val tvLazyListState = rememberTvLazyListState()
    TvLazyColumn(
        modifier = Modifier
            .padding(top = 80.dp)
            .height(700.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        state = tvLazyListState,
        content = {
            when (val carouselModel = catalogViewModel.carouselResponse.value) {
                is CarouselState.Loading -> {
                }

                is CarouselState.Success -> {
                    item {
                        FeaturedMoviesCarousel(
                            modifier = Modifier,
                            context = context,
                            responseCode = responseCode,
                            carouselList = carouselModel.carouselModel.body()!!.carousel,
                        )
                    }
                }

                is CarouselState.Failure -> {

                }

                is CarouselState.Empty -> {

                }

            }

            when (val tileModel = catalogViewModel.tileModelResponse.value) {
                is TileModelState.Loading -> {

                }

                is TileModelState.Success -> {
                    val rowList = catalogViewModel.row.value
                    Log.d("TAG", "FirstScreen: "+rowList!!.size)
                    items(
                        count = rowList.size,
//                        key = { index ->
//                            tileModel.tileModel.body()!!.rowName!!
//                        },
                    ) { index ->
                        Log.d(
                            "FirstScreen",
                            "FirstScreen: " + rowList[index].body()!!.rowName
                        )
                        MoviesRow(
                            context = context,
                            modifier = Modifier,
                            movies = rowList[index].body()!!,
                            responseCode = responseCode,
                            skinResource = skinResource,
                        )
                    }
                }

                is TileModelState.Failure -> {

                }

                is TileModelState.Empty -> {

                }
            }
        }

    )

}

