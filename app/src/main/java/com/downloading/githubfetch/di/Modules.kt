package com.downloading.githubfetch.di

import androidx.room.Room
import com.downloading.githubfetch.data.local.AppDatabase
import com.downloading.githubfetch.data.remote.GitHubService
import com.downloading.githubfetch.data.remote.GitHubService2
import com.downloading.githubfetch.data.repos.DBRepository
import com.downloading.githubfetch.data.repos.DBRepositoryImpl
import com.downloading.githubfetch.domain.usecase.DownloadRepositoryUseCase
import com.downloading.githubfetch.domain.usecase.GetDownloadedRepositoriesUseCase
import com.downloading.githubfetch.domain.usecase.SearchRepositoriesUseCase
import com.downloading.githubfetch.presentation.viewmodels.DownloadedVM
import com.downloading.githubfetch.presentation.viewmodels.SearchVM
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com/"
private const val BASE_URL2 = "https://github.com/"

val viewModelModule = module {
    viewModelOf(::SearchVM)
    viewModelOf(::DownloadedVM)
}

val databaseModule = module {
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app-database").build() }

    single<DBRepository> { DBRepositoryImpl(get()) }

    single { get<AppDatabase>().repositoryDao() }
}

val useCaseModule = module {
    single { SearchRepositoriesUseCase(get()) }
    single { DownloadRepositoryUseCase(get(), get ()) }
    single { GetDownloadedRepositoriesUseCase(get()) }
}

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(qualifier("retrofit2")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

val serviceModule = module {
    single { get<Retrofit>().create(GitHubService::class.java) }
    single { get<Retrofit>(qualifier = named("retrofit2")).create(GitHubService2::class.java) }
}


