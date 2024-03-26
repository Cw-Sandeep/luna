package tv.cloudwalker.lunalauncher.com.screens.common

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.TabRow
import androidx.tv.material3.TabRowDefaults
import tv.cloudwalker.lunalauncher.com.R
import tv.cloudwalker.lunalauncher.com.data.catsModel.Page
import tv.cloudwalker.lunalauncher.com.screens.AboutScreen
import tv.cloudwalker.lunalauncher.com.screens.HomeScreen
import tv.cloudwalker.lunalauncher.com.screens.OrbsAvatar
import tv.cloudwalker.lunalauncher.com.utils.Screens
import tv.cloudwalker.lunalauncher.com.utils.SkinResource


@OptIn(ExperimentalComposeUiApi::class, ExperimentalTvMaterial3Api::class)
@Composable
fun PageTabRow(
    modifier: Modifier,
    context: Context,
    skinResource: SkinResource,
    pageList: List<Page>,
    onScreenSelection: (screen: Page) -> Unit,
) {
    var currentTabSelected by remember { mutableStateOf(false) }
    val focusRequester: List<FocusRequester> =
        List(pageList.size + 1) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val selectedTabIndex by remember {
        mutableStateOf(0)
    }

    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.TopStart) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp)
                .wrapContentSize()
                .focusRestorer()
        ) {
            OrbsAvatar(
                modifier = Modifier
                    .wrapContentSize()
                    .focusRestorer()
                    .focusRequester(focusRequester[selectedTabIndex]),
                selected = false,
                skinResource = skinResource,
                icon = R.drawable.orb_profile_focused
            )
            Spacer(modifier = Modifier.size(40.dp))

            TabRow(
                modifier = Modifier
                    .wrapContentSize(),
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions: List<DpRect>, isActivated: Boolean ->
                    currentTabSelected = isActivated
                    tabPositions.getOrNull(selectedTabIndex)
                        .let { currentTabPosition ->
                            if (currentTabPosition != null) {
                                TabRowDefaults.PillIndicator(
                                    currentTabPosition = currentTabPosition,
                                    doesTabRowHaveFocus = currentTabSelected,
                                    activeColor = Color(
                                        context.resources.getColor(
                                            R.color.white
                                        )
                                    ),
                                    inactiveColor = Color(
                                        context.resources.getColor(
                                            R.color.tab_nonFocus
                                        )
                                    )
                                )
                            }
                        }
                },
                separator = { Spacer(modifier = Modifier.size(40.dp)) },
            ) {
                pageList.forEachIndexed { index, page ->
                    key(index) {
                        Tab(modifier = Modifier
                            .wrapContentSize()
                            .clip(CircleShape),
                            selected = selectedTabIndex == index,
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Transparent,
                            onClick = {
                                onScreenSelection(page)
                            },
                            content = {
                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(
                                            start = 10.dp,
                                            top = 5.dp,
                                            bottom = 5.dp,
                                            end = 10.dp
                                        ),
                                    text = page.pageName,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily(Font(R.font.nunito_semibold)),
                                        fontWeight = FontWeight(700),
                                        color =
                                        if (selectedTabIndex == index) {
                                            Color(0xFF00A3FF)
                                        } else {
                                            Color(0x66FFFFFF)
                                        },
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(40.dp))
            OrbsAvatar(
                modifier = Modifier.wrapContentSize(),
                icon = R.drawable.orb_search_focused,
                skinResource = skinResource,
                selected = false
            )
            Spacer(modifier = Modifier.size(20.dp))
            OrbsAvatar(
                modifier = Modifier.wrapContentSize(),
                icon = R.drawable.orb_settings_focused,
                skinResource = skinResource,
                selected = false
            )
            Spacer(modifier = Modifier.size(20.dp))
            OrbsAvatar(
                modifier = Modifier.wrapContentSize(),
                icon = R.drawable.orb_src_focused,
                skinResource = skinResource,
                selected = false
            )

        }
    }
}
