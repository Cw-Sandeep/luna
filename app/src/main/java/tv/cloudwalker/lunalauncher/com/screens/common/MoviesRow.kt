@file:OptIn(ExperimentalStdlibApi::class)

package tv.cloudwalker.lunalauncher.presentation.common

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.PivotOffsets
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import tv.cloudwalker.lunalauncher.com.R
import tv.cloudwalker.lunalauncher.com.data.catsModel.Tiles
import tv.cloudwalker.lunalauncher.com.data.catsModel.TilesModel
import tv.cloudwalker.lunalauncher.com.screens.common.MoviesRowItem
import tv.cloudwalker.lunalauncher.com.screens.common.OfflineMoviesRowItem
import tv.cloudwalker.lunalauncher.com.utils.Padding
import tv.cloudwalker.lunalauncher.com.utils.SkinResource
import tv.cloudwalker.lunalauncher.com.utils.createInitialFocusRestorerModifiers
import tv.cloudwalker.lunalauncher.com.utils.ifElse
import tv.cloudwalker.lunalauncher.com.utils.sdp

val MovieRowPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp)

@Composable
fun rememberChildPadding(direction: LayoutDirection = LocalLayoutDirection.current): Padding {
    return remember {
        Padding(
            start = MovieRowPadding.calculateStartPadding(direction),
            top = MovieRowPadding.calculateTopPadding(),
            end = MovieRowPadding.calculateEndPadding(direction),
            bottom = MovieRowPadding.calculateBottomPadding()
        )
    }
}


@OptIn(
    ExperimentalTvMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun MoviesRow(
    responseCode: Int,
    context: Context,
    skinResource: SkinResource,
    modifier: Modifier = Modifier,
    focusedItemIndex: (index: Int) -> Unit = {},
    movies: TilesModel,
) {
    var movieRowFocus by remember { mutableStateOf(false) }
    val focusRestorerModifiers = createInitialFocusRestorerModifiers()
    Column(
        modifier = modifier
            .padding(rememberChildPadding().start)
            .focusGroup(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        (if (movies.rowName.isNullOrEmpty()) {
            "MovieRow"
        } else {
            movies.rowName
        })?.let { rowName->
            Text(
                modifier = Modifier.padding(start = 30.dp, top = 10.dp, end = 10.dp),
                text = rowName,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                color = if (movieRowFocus) {
                    Color(0xFFFFFFFF)
                } else {
                    Color(0x1AFFFFFF)
                },
                textAlign = TextAlign.Center,
            )
        }

        AnimatedContent(
            targetState = movies.tiles,
            label = "",
        ) { tilesList ->
            TvLazyRow(
                modifier = Modifier
                    .then(focusRestorerModifiers.parentModifier)
                    .onFocusChanged {
                        movieRowFocus = it.hasFocus || it.isFocused
                    },
                state = rememberTvLazyListState(),
                pivotOffsets = PivotOffsets(parentFraction = 0.022f),
                contentPadding = PaddingValues(
                    rememberChildPadding().start,
                ),
                content = {
                    items(
                        count = tilesList.size,
                        key = { index ->
                            tilesList[index].tileId!!

                        },
                        contentType = { index ->
                            tilesList[index]
                        },
                        itemContent = { index ->
                            MoviesRowItem(
                                modifier = Modifier
                                    .ifElse(
                                        index == 0,
                                        focusRestorerModifiers.childModifier
                                    ),
                                context = context,
                                movie = tilesList[index],
                                skinResource = skinResource,
                                index = index,
                                focusedItemIndex = focusedItemIndex,
                                responseCode = responseCode
                            )
                        }
                    )

                }
            )
        }
    }
}

@OptIn(
    ExperimentalTvMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun OfflineMoviesRow(
    context: Context,
    modifier: Modifier = Modifier,
    title: String?,
    movies: List<Tiles?>,
) {
    var movieRowFocus by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .focusGroup()
    ) {
        if (title != null) {
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = "Recommended Apps",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                color = if (movieRowFocus) {
                    Color(0xFFFFFFFF)
                } else {
                    Color(0x1AFFFFFF)
                },
                textAlign = TextAlign.Center,
            )
        }
        AnimatedContent(
            targetState = movies,
            label = "",
        ) { movieState ->
            val focusRestorerModifiers = createInitialFocusRestorerModifiers()
            TvLazyRow(
                modifier = Modifier
                    .onFocusChanged {
                        movieRowFocus = it.hasFocus || it.isFocused
                    }
                    .focusRestorer(),
                pivotOffsets = PivotOffsets(parentFraction = 0.022f),
                contentPadding = PaddingValues(vertical = 6.sdp, horizontal = 6.sdp),
                content = {
                    movieState.forEachIndexed { index, tiles ->
                        item {
                            key(tiles!!.tileId) {
                                OfflineMoviesRowItem(
                                    modifier = Modifier
                                        .ifElse(
                                            index == 0,
                                            focusRestorerModifiers.childModifier
                                        )
                                        .weight(1f),
                                    context = context,
                                    offlineTiles = tiles
                                )
                            }
                        }

                    }
                }
            )
        }
    }
}


//image palette logic see again .....
//
//                        val imageLoader = LocalContext.current.imageLoader
//                        val request = ImageRequest.Builder(LocalContext.current)
//                            .data(urlFormatter(movie.posterUrl!![0]))
//                            .allowHardware(false)
//                            .build()
//
//                        LaunchedEffect(bgColor) {
//                            launch {
//                                imageLoader.execute(request).drawable?.let {
//                                    bgColor = Palette.from(it.toBitmap()).generate().getDominantColor(0XFFFFFF)
//                                    Log.d("MoviesRowItem", "MoviesRowItem: "+ Palette.from(it.toBitmap()).generate().getDominantColor(0XFFFFFF))
//                                }
//                            }
//                        }

