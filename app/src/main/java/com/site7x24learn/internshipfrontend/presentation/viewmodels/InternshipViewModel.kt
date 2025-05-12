package com.site7x24learn.internshipfrontend.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.site7x24learn.internshipfrontend.domain.models.internships.Category
import com.site7x24learn.internshipfrontend.domain.usecases.internships.CreateInternshipUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.GetCategoriesUseCase
import com.site7x24learn.internshipfrontend.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.site7x24learn.internshipfrontend.domain.usecases.internships.GetInternshipByIdUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.UpdateInternshipUseCase
import java.util.Collections.emptyList

@HiltViewModel
class InternshipViewModel @Inject constructor(
    private val getInternshipByIdUseCase: GetInternshipByIdUseCase,
    private val updateInternshipUseCase: UpdateInternshipUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createInternshipUseCase: CreateInternshipUseCase
) : ViewModel() {


    private val _state = mutableStateOf(InternshipState())
    val state: State<InternshipState> = _state

    init {
        getCategories()
    }
    private fun updateInternship(id: Int) {
        viewModelScope.launch {
            if (!validateForm()) {
                _state.value = _state.value.copy(
                    error = "Please fill all required fields",
                    isLoading = false
                )
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, error = null)
            when (val result = updateInternshipUseCase(
                id = id,
                title = _state.value.title,
                description = _state.value.description,
                deadline = _state.value.deadline,
                companyName = _state.value.company,
                categoryId = _state.value.selectedCategoryId
            )) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isSuccess = true,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Failed to update internship",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    // Loading state already set
                }
            }
        }
    }


    fun onEvent(event: InternshipEvent) {
        when (event) {
            is InternshipEvent.TitleChanged -> {
                _state.value = _state.value.copy(title = event.title)
            }
            is InternshipEvent.DescriptionChanged -> {
                _state.value = _state.value.copy(description = event.description)
            }
            is InternshipEvent.DeadlineChanged -> {
                _state.value = _state.value.copy(deadline = event.deadline)
            }
            is InternshipEvent.CompanyChanged -> {
                _state.value = _state.value.copy(company = event.company)
            }
            is InternshipEvent.CategoryChanged -> {
                _state.value = _state.value.copy(selectedCategoryId = event.categoryId)
            }
            is InternshipEvent.Update -> {
                updateInternship(event.id)
            }

            InternshipEvent.Submit -> {
                createInternship()
            }
            InternshipEvent.Reset -> {
                _state.value = InternshipState(categories = _state.value.categories)
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            when (val result = getCategoriesUseCase()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        categories = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Loading state already set
                }
            }
        }
    }

    private fun createInternship() {
        viewModelScope.launch {
            if (!validateForm()) {
                _state.value = _state.value.copy(
                    error = "Please fill all required fields",
                    isLoading = false
                )
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, error = null)
            when (val result = createInternshipUseCase(
                title = _state.value.title,
                description = _state.value.description,
                deadline = _state.value.deadline,
                companyName = _state.value.company,
                categoryId = _state.value.selectedCategoryId
            )) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isSuccess = true,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Failed to create internship",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Loading state already set
                }
            }
        }
    }
    fun loadInternship(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            when (val result = getInternshipByIdUseCase(id)) {
                is Resource.Success -> {
                    result.data?.let { internship ->
                        _state.value = _state.value.copy(
                            title = internship.title,
                            description = internship.description ?: "",
                            deadline = internship.deadline,
                            company = internship.companyName,
                            selectedCategoryId = internship.id,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Failed to load internship",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        return _state.value.title.isNotBlank() &&
                _state.value.company.isNotBlank() &&
                _state.value.deadline.isNotBlank() &&
                _state.value.selectedCategoryId != -1
    }


}

data class InternshipState(
    val title: String = "",
    val description: String = "",
    val deadline: String = "",
    val company: String = "",
    val selectedCategoryId: Int = -1,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class InternshipEvent {
    data class TitleChanged(val title: String) : InternshipEvent()
    data class DescriptionChanged(val description: String) : InternshipEvent()
    data class DeadlineChanged(val deadline: String) : InternshipEvent()
    data class CompanyChanged(val company: String) : InternshipEvent()
    data class CategoryChanged(val categoryId: Int) : InternshipEvent()
    data object Submit : InternshipEvent()
    data object Reset : InternshipEvent()
    data class Update(val id: Int) : InternshipEvent()
}