package com.downloading.githubfetch.data.repos

import com.downloading.githubfetch.data.local.RepositoryDao
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass

class DBRepositoryImpl(private val repositoryDao: RepositoryDao) : DBRepository {
    override suspend fun getAllRepositories(): List<GitRepoDBDataClass> {
        return repositoryDao.getAllRepositories()
    }

    override suspend fun insertRepo(repo: GitRepoDBDataClass) {
        repositoryDao.insertRepo(repo)
    }
}
