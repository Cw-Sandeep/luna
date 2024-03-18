package tv.cloudwalker.lunalauncher.com

import retrofit2.Response
import tv.cloudwalker.lunalauncher.com.data.catsModel.Catalog

sealed class ApiState {
    class Success(val catalog: Response<Catalog>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()
}