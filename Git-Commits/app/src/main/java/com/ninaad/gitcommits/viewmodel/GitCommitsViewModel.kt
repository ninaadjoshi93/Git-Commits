package com.ninaad.gitcommits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninaad.gitcommits.model.GitResponseItem
import com.ninaad.gitcommits.repository.GitCommitsRepository
import com.ninaad.gitcommits.repository.NetworkRequestError
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class GitCommitsViewModel @Inject constructor(private val repository: GitCommitsRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::GitCommitsViewModel)
    }

    private var repositoryOwner: String = "kubernetes"
    private var repositoryName: String = "kubernetes"

    private val showSpinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = showSpinner

    private val showSnackBar = MutableLiveData<String?>()
    val snackbar: LiveData<String?>
        get() = showSnackBar

    private val gitCommitsList = MutableLiveData<List<GitResponseItem>>()
    val commitsList: LiveData<List<GitResponseItem>>
        get() = gitCommitsList

    fun onSnackBarShown() {
        showSnackBar.value = null
    }

    fun getGitCommitsList() {
        launchDataLoad {
            val list = repository.getGitCommits(repositoryOwner, repositoryName)
            gitCommitsList.postValue(list)
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                showSpinner.value = true
                block()
            } catch (error: NetworkRequestError) {
                showSnackBar.value = error.message
                Timber.i(error.cause.toString())
            } finally {
                showSpinner.value = false
            }
        }
    }

    fun updateRepositoryOwner(owner: String) {
        repositoryOwner = owner
    }

    fun updateRepositoryName(name: String) {
        repositoryName = name
    }

    override fun onCleared() {
        super.onCleared()
    }

}