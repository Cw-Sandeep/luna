package tv.cloudwalker.lunalauncher.com.data.catsModel

import com.google.gson.annotations.SerializedName


data class Carousel (

  @SerializedName("target"   ) var target   : String? = null,
  @SerializedName("source"   ) var source   : String? = null,
  @SerializedName("imageUrl" ) var imageUrl : String? = null,
  @SerializedName("title"    ) var title    : String? = null

)