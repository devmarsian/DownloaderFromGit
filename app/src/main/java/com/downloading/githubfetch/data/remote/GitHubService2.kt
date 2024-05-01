package com.downloading.githubfetch.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface GitHubService2{
    @GET("{owner}/{repo}/archive/{branch}.zip")
    @Streaming
    suspend fun downloadRepositoryZip(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String
    ): Response<ResponseBody>
}
