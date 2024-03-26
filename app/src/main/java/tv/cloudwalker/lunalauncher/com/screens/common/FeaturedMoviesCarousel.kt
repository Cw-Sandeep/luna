package tv.cloudwalker.lunalauncher.com.screens.common

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Border
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Carousel
import androidx.tv.material3.CarouselDefaults
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import androidx.tv.material3.ToggleableSurfaceDefaults
import coil.compose.AsyncImage
import tv.cloudwalker.lunalauncher.com.R
import tv.cloudwalker.lunalauncher.com.data.catsModel.Carousel
import tv.cloudwalker.lunalauncher.com.utils.handleDPadKeyEvents
import tv.cloudwalker.lunalauncher.com.utils.urlFormatter
import tv.cloudwalker.lunalauncher.data.utilityServiceModel.DeeplinkRequest

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FeaturedMoviesCarousel(
    modifier: Modifier,
    responseCode: Int,
    context: Context,
    carouselList: List<Carousel?>?,
) {
    var carouselCurrentIndex by rememberSaveable { mutableIntStateOf(0) }
    val carouselState = remember { CarouselState(initialActiveItemIndex = carouselCurrentIndex) }
    var isCarouselFocused by remember { mutableStateOf(false) }
    LaunchedEffect(carouselState.activeItemIndex) {
        carouselCurrentIndex = carouselState.activeItemIndex
    }
    Carousel(
        modifier = modifier
            .fillMaxWidth()
            .height(313.dp)
            .onFocusChanged {
                isCarouselFocused = it.hasFocus
            }
            .handleDPadKeyEvents(onEnter = {
                if (responseCode == 401) {
                    Toast
                        .makeText(context, "Please Activate your License", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val deeplinkRequest = DeeplinkRequest()
                    deeplinkRequest.deeplink =
                        carouselList!![carouselState.activeItemIndex]!!.target
                    deeplinkRequest.sourceName =
                        carouselList[carouselState.activeItemIndex]!!.source
                    // openDeepLink(deeplinkRequest, context, coroutineScope)
                }
            }),
        autoScrollDurationMillis = 8000,
        itemCount = carouselList!!.size,
        carouselState = carouselState,
        carouselIndicator = {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 40.dp, bottom = 40.dp)
            ) {
                CarouselDefaults.IndicatorRow(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color.Transparent),
                    itemCount = carouselList.size,
                    activeItemIndex = carouselState.activeItemIndex,
                )
            }
        },
        contentTransformStartToEnd = fadeIn(tween(durationMillis = 2000)).togetherWith(
            fadeOut(tween(durationMillis = 2000))
        ),
        contentTransformEndToStart = fadeIn(tween(durationMillis = 2000)).togetherWith(
            fadeOut(tween(durationMillis = 2000))
        ),
        content = { index ->
            val carouselItem = remember { carouselList[index] }
            //openDeepLink(movie, context = LocalContext.current, rememberCoroutineScope())
            key(index) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(313.dp)
                        .drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x0003A9F4),
                                    Color(0x0003A9F4),
                                    Color(0x00000500),
                                    Color(0xFF000000)
                                ),
                                startY = 1f,
                                tileMode = TileMode.Mirror
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        },
                    model = (urlFormatter(carouselItem!!.imageUrl!!)),
                    contentDescription = "Description",
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 260.dp, start = 32.dp),
                    contentAlignment = Alignment.BottomStart,
                    content = {
                        WatchNowButton(
                            isCarouselFocused
                        )
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun WatchNowButton(
    isCarouselFocused: Boolean
) {
    val buttonContainerColor = if (isCarouselFocused) {
        Color(0xFFFFFFFF)
    } else {
        Color(0x0DFFFFFF)
    }
    val watchNowTextColor = if (isCarouselFocused) {
        Color(0xFF00A3FF)
    } else {
        Color(0x66FFFFFF)
    }
    Button(
        onClick = {
        },
        modifier = Modifier
            .wrapContentSize()
            .focusProperties {
                canFocus = true
            },
        tonalElevation = 20.dp,
        shape = ButtonDefaults.shape(shape = CircleShape),
        colors = ButtonDefaults.colors(
            containerColor = buttonContainerColor,
            contentColor = MaterialTheme.colorScheme.surface,
            focusedContentColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Text(
            text = "Watch Now",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontWeight = FontWeight(700),
                color = watchNowTextColor,
                textAlign = TextAlign.Center,
            )
        )
    }
}

