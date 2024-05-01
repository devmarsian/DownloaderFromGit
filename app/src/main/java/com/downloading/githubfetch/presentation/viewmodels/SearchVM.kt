package com.downloading.githubfetch.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.downloading.githubfetch.domain.model.GitRepositoryModel
import com.downloading.githubfetch.domain.usecase.DownloadRepositoryUseCase
import com.downloading.githubfetch.domain.usecase.SearchRepositoriesUseCase
import kotlinx.coroutines.launch
import java.io.IOException

class SearchVM(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val downloadRepositoryUseCase: DownloadRepositoryUseCase
) : ViewModel() {

    private val _repositories = MutableLiveData<List<GitRepositoryModel>>()
    val repositories: LiveData<List<GitRepositoryModel>> = _repositories

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    fun searchRepositories(user: String) {
        viewModelScope.launch {
            try {
                val repositories = searchRepositoriesUseCase.searchRepositories(user)
                _repositories.value = repositories
            } catch (e: IOException) {
                _showToast.value = "Error searching repositories: ${e.message}"
            }
        }
    }

    fun selectBranchAndDownload(repoOwner: String, repoName: String) {
        viewModelScope.launch {
            try {
                val branches = searchRepositoriesUseCase.getBranches(repoOwner, repoName)
                val branchNames = branches.map { it.name }.toTypedArray()
                val selectedBranch = branchNames.firstOrNull() ?: ""
                if (selectedBranch.isNotEmpty()) {
                    downloadRepository(repoOwner, repoName, selectedBranch)
                } else {
                    _showToast.value = "No branches."
                }
            } catch (e: IOException) {
                _showToast.value = "Error fetching branches: ${e.message}"
            }
        }
    }

    private suspend fun downloadRepository(repoOwner: String, repoName: String, branch: String) {
        try {
            downloadRepositoryUseCase.downloadRepository(repoOwner, repoName, branch)
            _showToast.value = "File downloaded successfully"
        } catch (e: IOException) {
            _showToast.value = "Error downloading file: ${e.message}"
        }
    }
}
