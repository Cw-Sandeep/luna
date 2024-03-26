package tv.cloudwalker.lunalauncher.com.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.foundation.ExperimentalTvFoundationApi
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.Border
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.tv.material3.ToggleableSurfaceDefaults
import tv.cloudwalker.lunalauncher.com.ApiState
import tv.cloudwalker.lunalauncher.com.R
import tv.cloudwalker.lunalauncher.com.screens.common.DashboardScreen
import tv.cloudwalker.lunalauncher.com.utils.Screens
import tv.cloudwalker.lunalauncher.com.utils.SkinResource
import tv.cloudwalker.lunalauncher.com.viewModel.CatalogViewModel

val TopBarTabs = Screens.entries.filter { it.isTabItem }

@OptIn(
    ExperimentalTvMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class, ExperimentalTvFoundationApi::class
)
@Composable
fun MainScreen(
    skinResource: SkinResource,
    context: Context,
    kFunction1: (OnBackPressedDispatcher) -> Unit
) {
    val catalogViewModel: CatalogViewModel = viewModel()
    val TAG = "MainScreen"


    LaunchedEffect(Unit) {
        catalogViewModel.getCatalog()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        when (val catalogResult = catalogViewModel.catalogResponse.value) {
            is ApiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize(),
                        color = Color.Blue
                    )
                }
            }

            is ApiState.Success -> {
                if (catalogResult.catalog.body()!!.pages.isNotEmpty()) {
                    val pageList = catalogResult.catalog.body()!!.pages
                  //  val focusRequester: List<FocusRequester> = List(pageList.size + 1) { FocusRequester() }

                    val navHostController = rememberNavController()
                    NavHost(
                        navController = navHostController,
                        startDestination = "DashBoardScreen",
                        enterTransition = {
                            fadeIn(animationSpec = tween(700))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(700))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(700))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(700))
                        },
                    ) {
                        composable("DashBoardScreen") {
                            TvLazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                userScrollEnabled = true,
                                contentPadding = PaddingValues(bottom = 20.dp),
                                state = rememberTvLazyListState(),
                            ) {
                                item {
                                    DashboardScreen(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        //  .background(brush = Brush.verticalGradient(colorList))
                                        skinResource = skinResource,
                                        responseCode = catalogResult.catalog.code(),
                                        profileImageUrl = null,
                                        context = context,
                                        onBackPressed = kFunction1,
                                        catalog = catalogResult.catalog.body()!!,
                                        catalogViewModel = catalogViewModel,
                                    )
                                }


                            }
                        }

                    }


                }
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

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Text(text = "Home", color = Color.White)
    }
}

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Text(text = "About", color = Color.White)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ProfileOrb(
    modifier: Modifier,
    selected: Boolean,
) {
    Surface(
        modifier = modifier
            .wrapContentSize()
            .background(Color.Transparent),
        scale = ToggleableSurfaceDefaults.scale(focusedScale = 1.1f),
        shape = ToggleableSurfaceDefaults.shape(shape = RoundedCornerShape(50.dp)),
        border = ToggleableSurfaceDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(
                    width = 2.dp,
                    color = Color.Cyan
                ),
            ),
        ),
        colors = ToggleableSurfaceDefaults.colors(
            containerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            contentColor = Color.Transparent,
            focusedContentColor = Color.Transparent
        ),
        checked = selected,
        onCheckedChange = { }
    ) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp),
            painter = painterResource(id = R.drawable.orb_profile),
            contentDescription = "Profile Image"

        )
    }
}


@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun OrbsAvatar(
    selected: Boolean,
    icon: Int,
    skinResource: SkinResource,
    modifier: Modifier,
) {
    Surface(
        modifier = modifier
            .width(40.dp)
            .height(40.dp),
        scale = ToggleableSurfaceDefaults.scale(focusedScale = 1.1f),
        shape = ToggleableSurfaceDefaults.shape(shape = RoundedCornerShape(50.dp)),
        colors = ToggleableSurfaceDefaults.colors(
            focusedContainerColor = Color(
                skinResource.getColorFromSKinApp(
                    "orb_focus",
                    R.color.orb_focus
                )
            ),
            focusedContentColor = Color(
                skinResource.getColorFromSKinApp(
                    "orb_focus",
                    R.color.orb_focus
                )
            ),
            containerColor = if (selected) {
                Color(
                    skinResource.getColorFromSKinApp(
                        "orb_focus",
                        R.color.orb_focus
                    )
                )
            } else {
                Color(0x0DD9D9D9)
            }
        ),
        checked = selected,
        onCheckedChange = { }
    ) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .onFocusChanged {
                },
            painter = painterResource(icon),
            contentDescription = "image description",
            contentScale = ContentScale.None
        )
    }
}
