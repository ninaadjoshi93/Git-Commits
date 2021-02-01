package com.ninaad.gitcommits

import com.google.gson.Gson
import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.model.GitResponseItem
import com.ninaad.gitcommits.repository.GitCommitsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset

class GitCommitsRepositoryTest {

    lateinit var sut: GitCommitsRepository

    @MockK
    lateinit var mockApiClass: GitHubAPI

    lateinit var output: List<GitResponseItem>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = GitCommitsRepository(mockApiClass)
        coEvery {
            mockApiClass.getCommits("owner", "name")
        } returns emptyList()

        val inputStream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream("get_commits_success_response.json")
        val reader = inputStream.bufferedReader(Charset.defaultCharset())
        try {
            val content = reader.readText()
            output = Gson().fromJson(content, Array<GitResponseItem>::class.java).toList()
            coEvery {
                mockApiClass.getCommits("kubernetes", "kubernetes")
            } returns output
        } catch (exception: IOException) {
            Timber.i("Resource file not found")
        } finally {
            reader.close()
        }
    }

    @Test
    fun testGetGitCommitsEmptyMethod() = runBlocking {
        val repoOwner = "owner"
        val repoName = "name"
        val expected = emptyList<GitResponseItem>()
        val actual = sut.getGitCommits(repoOwner, repoName)
        assertEquals(expected, actual)
    }

    @Test
    fun testGetGitCommitsValidMethod() = runBlocking {
        val repoOwner = "kubernetes"
        val repoName = "kubernetes"
        val expected = ArrayList(output)
        val actual = sut.getGitCommits(repoOwner, repoName)
        assertEquals(expected, actual)
        assertEquals(expected.size, actual.size)
    }

    @After
    fun tearDown() {

    }
}