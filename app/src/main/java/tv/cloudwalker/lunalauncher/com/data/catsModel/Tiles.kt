package tv.cloudwalker.lunalauncher.com.data.catsModel

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName


@Stable
data class Tiles(

  @SerializedName("title"        ) var title: String?           = null,
  @SerializedName("videoUrl"     ) var videoUrl: ArrayList<String>? = arrayListOf(),
  @SerializedName("tileType"     ) var tileType: String?           = null,
  @SerializedName("posterUrl"    ) var posterUrl: ArrayList<String>? = arrayListOf(),
  @SerializedName("portraitUrl"  ) var portraitUrl: ArrayList<String> ? = arrayListOf(),
  @SerializedName("isDetailPage" ) var isDetailPage: Boolean?          = null,
  @SerializedName("source"       ) var source: String?           = null,
  @SerializedName("tileId"       ) var tileId: String?           = null,
  @SerializedName("deeplink"     ) var deeplink: String?           = null,
  @SerializedName("packageName"  ) var packageName: String?           = null,
  var drawable: Drawable?,

  )