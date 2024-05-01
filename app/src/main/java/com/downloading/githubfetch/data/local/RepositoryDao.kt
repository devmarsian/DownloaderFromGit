package com.downloading.githubfetch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass


@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository")
    suspend fun getAllRepositories(): List<GitRepoDBDataClass>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: GitRepoDBDataClass)

}