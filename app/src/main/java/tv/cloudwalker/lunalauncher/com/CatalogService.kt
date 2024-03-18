package tv.cloudwalker.lunalauncher.com

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import tv.cloudwalker.lunalauncher.com.data.catsModel.CarouselModel
import tv.cloudwalker.lunalauncher.com.data.catsModel.Catalog
import tv.cloudwalker.lunalauncher.com.data.catsModel.TilesModel

interface CatalogService {

    @GET("tileService/v1/catalog/cloudwalker/tp.sk518d.pb802/youtube")
    suspend fun getCatalog(): Response<Catalog>

    @GET
    suspend fun getCarousel(@Url carouselEndpoint: String): Response<CarouselModel>

    @GET
    suspend fun getRows(@Url rowContentEndpoint: String): Response<TilesModel>


}