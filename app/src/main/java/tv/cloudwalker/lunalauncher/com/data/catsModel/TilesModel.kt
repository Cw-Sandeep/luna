package tv.cloudwalker.lunalauncher.com.data.catsModel

import com.google.gson.annotations.SerializedName
import tv.cloudwalker.lunalauncher.com.data.catsModel.Tiles


data class TilesModel (

  @SerializedName("rowName"   ) var rowName   : String?          = null,
  @SerializedName("rowLayout" ) var rowLayout : String?          = null,
  @SerializedName("tiles"     ) var tiles     : ArrayList<Tiles> = arrayListOf()

)