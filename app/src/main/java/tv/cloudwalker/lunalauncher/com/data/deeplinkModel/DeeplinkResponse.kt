package tv.cloudwalker.lunalauncher.data.utilityServiceModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import tv.cloudwalker.lunalauncher.com.data.deeplinkModel.AppExtra
import tv.cloudwalker.lunalauncher.com.data.deeplinkModel.MarketAppExtra

data class DeeplinkResponse(

    @SerializedName("appPackageName") @Expose
     var appPackageName: String? = null,

    @SerializedName("appDeeplink")
    @Expose
     val appDeeplink: String? = null,

    @SerializedName("appClassName")
    @Expose
     val appClassName: String? = null,

    @SerializedName("appCategory")
    @Expose
     val appCategory: String? = null,

    @SerializedName("appFlags")
    @Expose
     val appFlags: List<String>? = null,

    @SerializedName("appAction")
    @Expose
     val appAction: String? = null,

    @SerializedName("appExtras")
    @Expose
     val appExtras: List<AppExtra>? = null,

    @SerializedName("marketPackageName")
    @Expose
     val marketPackageName: String? = null,

    @SerializedName("marketDeeplink")
    @Expose
     val marketDeeplink: String? = null,

    @SerializedName("marketClassName")
    @Expose
     val marketClassName: String? = null,

    @SerializedName("marketCategory")
    @Expose
     val marketCategory: String? = null,

    @SerializedName("marketFlags")
    @Expose
     val marketFlags: List<Any>? = null,

    @SerializedName("marketAction")
    @Expose
     val marketAction: String? = null,

    @SerializedName("marketExtras")
    @Expose
     val marketExtras: List<MarketAppExtra>? = null,

    @SerializedName("intentType")
    @Expose
     val intentType: String? = null,

    @SerializedName("makeDefaultIntent")
    @Expose
     val makeDefaultIntent: Boolean? = null
)