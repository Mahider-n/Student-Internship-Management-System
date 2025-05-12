package com.site7x24learn.internshipfrontend.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.site7x24learn.internshipfrontend.domain.models.internships.Internship
import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternshipListViewModel @Inject constructor(
    private val internshipRepository: InternshipRepository
) : ViewModel() {

    private val _state = MutableStateFlow(InternshipListState())
    val state: StateFlow<InternshipListState> = _state

    init {
        loadInternships()
    }

    fun loadInternships() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = internshipRepository.getInternships()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            internships = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            internships = emptyList(),
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun deleteInternship(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = internshipRepository.deleteInternship(id)) {
                is Resource.Success -> {
                    if (result.data) {
                        loadInternships() // refresh list on successful delete
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Deletion unsuccessful"
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                is Resource.Loading -> {
                    // Optionally set loading here if needed
                }
            }
        }
    }
    fun onErrorMessageShown() {
        _state.update { it.copy(errorMessage = null) }
    }


}

data class InternshipListState(
    val internships: List<Internship> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)