package com.downloading.githubfetch.domain.model

data class GitRepositoryModel(val id: Long, val name: String, val description: String, val html_url: String, val owner: Owner)
data class Owner(val login: String)
data class Branch(val name: String)