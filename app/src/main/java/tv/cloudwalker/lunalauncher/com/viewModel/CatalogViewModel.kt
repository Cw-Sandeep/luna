package tv.cloudwalker.lunalauncher.com.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.Response
import tv.cloudwalker.lunalauncher.com.ApiState
import tv.cloudwalker.lunalauncher.com.CarouselState
import tv.cloudwalker.lunalauncher.com.TileModelState
import tv.cloudwalker.lunalauncher.com.data.catsModel.CarouselModel
import tv.cloudwalker.lunalauncher.com.data.catsModel.TilesModel
import tv.cloudwalker.lunalauncher.com.repository.CatalogRepository
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository,
) :
    ViewModel() {
    val catalogResponse: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    val carouselResponse: MutableState<CarouselState> = mutableStateOf(CarouselState.Empty)
    val tileModelResponse: MutableState<TileModelState> = mutableStateOf(TileModelState.Empty)
    private val _row = MutableLiveData<List<Response<TilesModel>>>()
    val row: MutableLiveData<List<Response<TilesModel>>> = _row
    fun getCatalog() {
        viewModelScope.launch {
            catalogRepository.getCatalog().onStart {
                catalogResponse.value = ApiState.Loading
            }.catch {
                catalogResponse.value = ApiState.Failure(it)
            }.collect {
                catalogResponse.value = ApiState.Success(it)
            }
        }

    }

    fun getCarousel(carouselEndPoint: String) {
        viewModelScope.launch {
            catalogRepository.getCarousel(carouselEndPoint).onStart {
                carouselResponse.value = CarouselState.Loading
            }.catch {
                carouselResponse.value = CarouselState.Failure(it)
            }.collect {
                carouselResponse.value = CarouselState.Success(it)
            }
        }

    }

    fun getRow(rowEndPoint: List<String>) {
        var row: TilesModel?
        viewModelScope.launch {
            for (i in rowEndPoint) {
                Log.d("getRow", "getRow: " + i)
                catalogRepository.getRow(i).onStart {
                    tileModelResponse.value = TileModelState.Loading
                }.catch {
                    tileModelResponse.value = TileModelState.Failure(it)
                }.collectIndexed { index, value ->
                    Log.d("TAG", "getRow: " + value.body()!!.rowName)
                    _row.value = listOf(value)
                    tileModelResponse.value = TileModelState.Success(listOf(value))
                }
            }
        }
    }

}

