package com.ninaad.gitcommits

import com.google.gson.Gson
import com.ninaad.gitcommits.model.GitResponseItem
import com.ninaad.gitcommits.repository.GitCommitsRepository
import com.ninaad.gitcommits.util.NetworkUtil
import com.ninaad.gitcommits.viewmodel.GitCommitsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset

@ExperimentalCoroutinesApi
class GitCommitsViewModelTest {

    lateinit var sut: GitCommitsViewModel

    @MockK
    lateinit var repository: GitCommitsRepository

    @MockK
    lateinit var networkUtil: NetworkUtil

    lateinit var output: List<GitResponseItem>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GitCommitsViewModel(repository, networkUtil)
        every {
            networkUtil.isNetworkAvailable()
        } returns true

        coEvery {
            repository.getGitCommits("owner", "name")
        } coAnswers {
            emptyList()
        }

        val inputStream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream("get_commits_success_response.json")
        val reader = inputStream.bufferedReader(Charset.defaultCharset())
        try {
            val content = reader.readText()
            output = Gson().fromJson(content, Array<GitResponseItem>::class.java).toList()
            coEvery {
                repository.getGitCommits("kubernetes", "kubernetes")
            } coAnswers {
                output
            }

        } catch (exception: IOException) {
            Timber.i("Resource file not found")
        } finally {
            reader.close()
        }
    }

    @Test
    fun testUpdateRepositoryOwner() {
        val owner = "owner"
        sut.updateRepositoryOwner(owner)
        val actual = sut.getRepositoryOwner()
        assertEquals(owner, actual)
    }

    @Test
    fun testUpdateRepositoryName() {
        val name = "name"
        sut.updateRepositoryName(name)
        val actual = sut.getRepositoryName()
        assertEquals(name, actual)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}