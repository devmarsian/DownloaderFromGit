package com.downloading.githubfetch.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass
import com.downloading.githubfetch.domain.usecase.GetDownloadedRepositoriesUseCase
import kotlinx.coroutines.launch

class DownloadedVM(
    private val getDownloadedRepositoriesUseCase: GetDownloadedRepositoriesUseCase
) : ViewModel() {

    private val _repositories = MutableLiveData<List<GitRepoDBDataClass>>()
    val repositories: LiveData<List<GitRepoDBDataClass>> = _repositories

    init {
        loadRepositories()
    }

    private fun loadRepositories() {
        viewModelScope.launch {
            try {
                val repositories = getDownloadedRepositoriesUseCase.getDownloadedRepositories()
                _repositories.postValue(repositories)
            } catch (e: Exception) {
                Log.e("DownloadedVM", "Error loading repositories: ${e.message}")
            }
        }
    }
}
