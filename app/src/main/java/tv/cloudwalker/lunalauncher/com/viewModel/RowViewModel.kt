package tv.cloudwalker.lunalauncher.com.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tv.cloudwalker.lunalauncher.com.repository.CatalogRepository
import javax.inject.Inject

@HiltViewModel
class RowViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository,
) : ViewModel() {
}