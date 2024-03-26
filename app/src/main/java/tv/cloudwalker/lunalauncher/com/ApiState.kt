package tv.cloudwalker.lunalauncher.com

import retrofit2.Response
import tv.cloudwalker.lunalauncher.com.data.catsModel.CarouselModel
import tv.cloudwalker.lunalauncher.com.data.catsModel.Catalog
import tv.cloudwalker.lunalauncher.com.data.catsModel.TilesModel

sealed class ApiState {
    class Success(val catalog: Response<Catalog>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    data object Loading : ApiState()
    data object Empty : ApiState()
}

sealed class CarouselState {
    class Success(val carouselModel: Response<CarouselModel>) : CarouselState()
    class Failure(val msg: Throwable) : CarouselState()
    data object Loading : CarouselState()
    data object Empty : CarouselState()
}


sealed class TileModelState {
    class Success(val tileModel: List<Response<TilesModel>>) : TileModelState()
    class Failure(val msg: Throwable) : TileModelState()
    data object Loading : TileModelState()
    data object Empty : TileModelState()
}