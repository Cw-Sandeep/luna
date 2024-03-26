package tv.cloudwalker.lunalauncher.com.screens.common

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tv.cloudwalker.lunalauncher.com.data.catsModel.Catalog
import tv.cloudwalker.lunalauncher.com.utils.SkinResource
import tv.cloudwalker.lunalauncher.com.viewModel.CatalogViewModel
import tv.cloudwalker.lunalauncher.presentation.screens.FirstScreen


@Composable
fun DashboardScreen(
    modifier: Modifier,
    catalog: Catalog,
    profileImageUrl: String?,
    skinResource: SkinResource,
    context: Context,
    catalogViewModel: CatalogViewModel,
    onBackPressed: (OnBackPressedDispatcher) -> Unit,
    responseCode: Int,
) {

    val navController = rememberNavController()
    var isTopBarVisible by remember { mutableStateOf(true) }
    val isTopBarFocused by remember { mutableStateOf(false) }

    var currentDestination: String? by remember { mutableStateOf(null) }


    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            Log.d("DashboardScreen", "DashboardScreen: " + destination.route)
            currentDestination = destination.route
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }


    BackPressHandledArea(
        onBackPressed = {
        })

    {
        PageTabRow(
            modifier = Modifier,
            context = context,
            skinResource = skinResource,
            pageList = catalog.pages,
        ) { screen ->
            navController.navigate(screen()) {
                launchSingleTop = true
            }
        }
        Body(
            responseCode = responseCode,
            catalog = catalog,
            skinResource = skinResource,
            navController = navController,
            modifier = Modifier,
            catalogViewModel = catalogViewModel,
        )


    }
}

@Composable
fun Body(
    catalog: Catalog,
    skinResource: SkinResource,
    catalogViewModel: CatalogViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    responseCode: Int,
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = catalog.pages[0].pageName,
        builder = {
            composable(catalog.pages[0].pageName) {
                FirstScreen(
                    catalog = catalog,
                    responseCode = responseCode,
                    skinResource = skinResource,
                    catalogViewModel = catalogViewModel,
                )
            }
        }
    )


}

@Composable
private fun BackPressHandledArea(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) =
    Box(
        modifier = Modifier
            .onPreviewKeyEvent {
                if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
                    onBackPressed()
                    true
                } else {
                    false
                }
            }
            .then(modifier),
        content = content
    )
