package com.downloading.githubfetch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class GitRepoDBDataClass(
    @PrimaryKey val name: String,
    val owner: String
)
