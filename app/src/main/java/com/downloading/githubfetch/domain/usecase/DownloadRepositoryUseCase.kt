package com.downloading.githubfetch.domain.usecase

import android.os.Environment
import com.downloading.githubfetch.data.remote.GitHubService2
import com.downloading.githubfetch.data.repos.DBRepository
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class DownloadRepositoryUseCase(private val service: GitHubService2, private val dbRepository: DBRepository) {

    suspend fun downloadRepository(repoOwner: String, repoName: String, branch: String) {
        val response = service.downloadRepositoryZip(repoOwner, repoName, branch)
        if (response.isSuccessful) {
            val repo = GitRepoDBDataClass(repoName, repoOwner)
            dbRepository.insertRepo(repo)
            saveFile(response.body(), "$repoName.zip")
        } else {
            throw IOException("Download failed with status code: ${response.code()}")
        }
    }

    private fun saveFile(body: ResponseBody?, fileName: String) {
        body ?: return

        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, fileName)

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            inputStream = body.byteStream()
            outputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
        } catch (e: IOException) {
            throw IOException("Error saving file: ${e.message}")
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }
}
