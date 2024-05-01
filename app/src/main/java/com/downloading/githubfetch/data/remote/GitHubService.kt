package com.downloading.githubfetch.data.remote

import com.downloading.githubfetch.domain.model.Branch
import com.downloading.githubfetch.domain.model.GitRepositoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("repos/{owner}/{repo}/branches")
    suspend fun getBranches(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<Branch>>

    @GET("users/{owner}/repos")
    suspend fun getUserRepositories(
        @Path("owner") owner: String
    ): Response<List<GitRepositoryModel>>
}

