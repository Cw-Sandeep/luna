package tv.cloudwalker.lunalauncher.com.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import tv.cloudwalker.lunalauncher.com.CatalogService
import tv.cloudwalker.lunalauncher.com.data.catsModel.Catalog
import tv.cloudwalker.lunalauncher.com.data.catsModel.TilesModel
import javax.inject.Inject


class CatalogRepository @Inject constructor(private val catalogService: CatalogService) {

    fun getCatalog(): Flow<Response<Catalog>> = flow {
        emit(catalogService.getCatalog())
    }.flowOn(Dispatchers.IO)

}