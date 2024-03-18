package tv.cloudwalker.lunalauncher.com.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import tv.cloudwalker.lunalauncher.com.ApiState
import tv.cloudwalker.lunalauncher.com.repository.CatalogRepository
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository,
) :
    ViewModel() {
    val catalogResponse: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {
        getCatalog()
    }

    private fun getCatalog() {
        viewModelScope.launch {
            catalogRepository.getCatalog().onStart {
                catalogResponse.value = ApiState.Loading
                delay(5000)
            }.catch {
                catalogResponse.value = ApiState.Failure(it)
            }.collect{
                catalogResponse.value = ApiState.Success(it)
            }
        }

    }


}

