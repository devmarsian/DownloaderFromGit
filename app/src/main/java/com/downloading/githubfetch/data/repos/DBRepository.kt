package com.downloading.githubfetch.data.repos

import com.downloading.githubfetch.domain.model.GitRepoDBDataClass


interface DBRepository {
    suspend fun getAllRepositories(): List<GitRepoDBDataClass>
    suspend fun insertRepo(repo: GitRepoDBDataClass)
}