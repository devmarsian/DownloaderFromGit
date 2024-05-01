package com.downloading.githubfetch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass

@Database(entities = [GitRepoDBDataClass::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}


