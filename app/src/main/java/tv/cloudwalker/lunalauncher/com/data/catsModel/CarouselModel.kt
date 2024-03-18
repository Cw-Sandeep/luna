package tv.cloudwalker.lunalauncher.com.data.catsModel

import com.google.gson.annotations.SerializedName
import tv.cloudwalker.lunalauncher.com.data.catsModel.Carousel


data class CarouselModel (

  @SerializedName("carousel" ) var carousel : List<Carousel> = arrayListOf()

)