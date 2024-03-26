@file:OptIn(ExperimentalComposeUiApi::class)

package tv.cloudwalker.lunalauncher.com.screens.common

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import tv.cloudwalker.lunalauncher.com.R
import tv.cloudwalker.lunalauncher.com.data.catsModel.Tiles
import tv.cloudwalker.lunalauncher.com.utils.MovieCardDimens
import tv.cloudwalker.lunalauncher.com.utils.SkinResource
import tv.cloudwalker.lunalauncher.com.utils.sdp
import tv.cloudwalker.lunalauncher.com.utils.urlFormatter
import tv.cloudwalker.lunalauncher.data.utilityServiceModel.DeeplinkRequest


val MovieCardPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp)

@Composable
fun rememberMovieCard(): MovieCardDimens {
    return remember {
        MovieCardDimens(
            cardWidth = 300.dp,
            cardHeight = 180.dp,
            start = 6.dp,
            top = MovieCardPadding.calculateTopPadding(),
            end = 8.dp,
            bottom = MovieCardPadding.calculateBottomPadding()
        )
    }
}


@OptIn(
    ExperimentalTvMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun MoviesRowItem(
    responseCode: Int,
    context: Context,
    modifier: Modifier,
    skinResource: SkinResource,
    focusedItemIndex: (index: Int) -> Unit,
    movie: Tiles,
    index: Int
) {
    val colorList = remember {
        listOf(Color.Cyan, Color.Green, Color.Blue, Color.Magenta, Color.Red)
    }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .width(rememberMovieCard().cardWidth)
            .height(rememberMovieCard().cardHeight)
            .padding(rememberMovieCard().start, rememberMovieCard().end)
            .then(modifier),
        scale = CardDefaults.scale(1f, 1f, 1f),
        onClick = {
            if (responseCode == 401) {
                Toast.makeText(context, "Please Activate your License", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val deeplinkRequest = DeeplinkRequest()
                deeplinkRequest.deeplink = movie.deeplink
                deeplinkRequest.packageName = movie.packageName
                deeplinkRequest.sourceName = movie.source
             //   openDeepLink(deeplinkRequest, context, coroutineScope)
            }
        },
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(
                    width = 4.dp,
                    color = Color(
                        skinResource.getColorFromSKinApp(
                            "launcher_tile_focus",
                            R.color.launcher_tile_focus
                        )
                    )
                ),
            )
        ),
        colors = CardDefaults.compactCardColors(
            containerColor = Color.Transparent
        ),
        shape = CardDefaults.shape(shape = RoundedCornerShape(10.dp)),
        interactionSource = remember { MutableInteractionSource() },
    ) {
        var imageUrl = ""
        if (movie.posterUrl!!.isNotEmpty() && movie.posterUrl!!.size == 1) {
            imageUrl = urlFormatter(movie.posterUrl!![0])
        } else if (movie.posterUrl!!.size == 2) {
            imageUrl = urlFormatter(movie.posterUrl!![1])
        }
        AsyncImage(
            modifier = Modifier
                .width(rememberMovieCard().cardWidth)
                .height(rememberMovieCard().cardHeight),
            model = ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .allowRgb565(true)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .allowHardware(true)
                .networkCachePolicy(CachePolicy.ENABLED)
                .data(imageUrl)
                .build(),
            contentDescription = "movie poster",
            contentScale = ContentScale.FillBounds
        )
    }

}


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun OfflineMoviesRowItem(
    context: Context,
    modifier: Modifier,
    offlineTiles: Tiles,
) {
    val colorList = remember {
        listOf(Color.Cyan, Color.Green, Color.Blue, Color.Magenta, Color.Red)
    }
    var isItemFocused by remember { mutableStateOf(false) }
    val tileFocusColor by remember { mutableStateOf(Color(0xFF2B8EDC)) }
    val widthInDp = 270 // Your width in dp
    val heightInDp = 150
    Card(
        modifier = modifier
            .width(widthInDp.dp)
            .height(heightInDp.dp)
            .onFocusChanged {
                isItemFocused = it.isFocused || it.hasFocus
            }
            .padding(start = 5.sdp, end = 5.sdp),
        //zoom factor similar to leanback the more
        scale = CardDefaults.scale(1f, 1f, 1f),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(
                    width = 3.dp, color = tileFocusColor
                ),
            )
        ),
        interactionSource = remember { MutableInteractionSource() },
        shape = CardDefaults.shape(shape = RoundedCornerShape(10.dp)),
        onClick = {
            Toast.makeText(context, "Please Connect to Internet", Toast.LENGTH_SHORT).show()
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .width(widthInDp.dp)
                .height(heightInDp.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(offlineTiles.drawable)
                .error(R.drawable.errorimage)
                .diskCachePolicy(CachePolicy.ENABLED)
                .allowHardware(true)
                .allowConversionToBitmap(true)
                .allowRgb565(true)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .build(),
            contentDescription = "movie poster",
            contentScale = ContentScale.FillBounds
        )
    }

}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieDescription(showItemDescription: Boolean, isItemFocused: Boolean, movie: Tiles) {
    if (showItemDescription) {
        val movieNameAlpha by animateFloatAsState(
            targetValue = if (isItemFocused) 1f else 0f,
            label = "",
        )
        Text(
            text = "Small Description about the Movie",
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .alpha(movieNameAlpha)
                .fillMaxWidth()
                .padding(top = 4.sdp, start = 8.sdp),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )

    }
}
