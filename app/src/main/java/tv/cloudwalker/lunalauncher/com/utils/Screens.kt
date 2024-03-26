

package tv.cloudwalker.lunalauncher.com.utils

import androidx.annotation.DrawableRes

enum class Screens(
    private val args: List<String>? = null,
    val isTabItem: Boolean = false,
    @DrawableRes val tabIcon: Int? = null,
) {
    Home(isTabItem = true),
    Kids(isTabItem = true),
    FamilyTime(isTabItem = true),
    Songs(isTabItem = true),
    Movies(isTabItem = true),
    OfflineScreen,
    Dashboard,
    VideoPlayer;

    operator fun invoke(): String {
        val argList = StringBuilder()
        args?.let { nnArgs ->
            nnArgs.forEach { arg -> argList.append("/{$arg}") }
        }
        return name + argList
    }

    fun withArgs(vararg args: Any): String {
        val destination = StringBuilder()
        args.forEach { arg -> destination.append("/$arg") }
        return name + destination
    }
}

enum class OfflineScreen(
    private val args: List<String>? = null,
    val isTabItem: Boolean = false,
    @DrawableRes val tabIcon: Int? = null,
) {
    Home(isTabItem = true);

    operator fun invoke(): String {
        val argList = StringBuilder()
        args?.let { nnArgs ->
            nnArgs.forEach { arg -> argList.append("/{$arg}") }
        }
        return name + argList
    }

    fun withArgs(vararg args: Any): String {
        val destination = StringBuilder()
        args.forEach { arg -> destination.append("/$arg") }
        return name + destination
    }
}
