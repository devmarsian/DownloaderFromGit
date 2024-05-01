package com.downloading.githubfetch.domain.usecase

import com.downloading.githubfetch.data.repos.DBRepository
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass

class GetDownloadedRepositoriesUseCase(private val dbRepository: DBRepository) {

    suspend fun getDownloadedRepositories(): List<GitRepoDBDataClass> {
        return dbRepository.getAllRepositories()
    }
}
