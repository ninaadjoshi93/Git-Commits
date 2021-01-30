package com.ninaad.gitcommits.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninaad.gitcommits.model.GitResponseItem
import com.ninaad.gitcommits.repository.GitCommitsRepository
import com.ninaad.gitcommits.repository.NetworkRequestError
import com.ninaad.gitcommits.util.NetworkUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitCommitsViewModel @Inject constructor(private val repository: GitCommitsRepository, private val networkUtil: NetworkUtil) : ViewModel() {

    companion object {
        val FACTORY = dualArgViewModelFactory(::GitCommitsViewModel)
    }

    private var repositoryOwner: String = ""
    private var repositoryName: String = ""

    private val _showSpinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _showSpinner

    private val _showSnackBar = MutableLiveData<String?>()
    val snackBar: LiveData<String?>
        get() = _showSnackBar

    private val _gitCommitsList = MutableLiveData<List<GitResponseItem>>()
    val gitCommitsList: LiveData<List<GitResponseItem>>
        get() = _gitCommitsList

    fun onSnackBarShown() {
        _showSnackBar.value = null
    }

    fun isNetworkAvailable(context: Context): Boolean {
        return networkUtil.isNetworkAvailable(context)
    }

    fun getRepositoryOwner(): String {
        return repositoryOwner
    }

    fun updateRepositoryOwner(owner: String){
        repositoryOwner = owner
        Timber.i("value of = $repositoryOwner")
    }

    fun getRepositoryName(): String {
        return repositoryName
    }

    fun updateRepositoryName(name: String){
        repositoryName = name
        Timber.i("value of = $repositoryName")
    }

    fun onGetGitCommitsListButtonClick() {
        if (repositoryOwner.trim().isEmpty()) {
            _showSnackBar.value = "Please Enter Github Repository Owner"
            return
        }
        if (repositoryName.trim().isEmpty()) {
            _showSnackBar.value = "Please Enter Github Repository Name"
            return
        }
    }

    fun getGitCommitsList() {
        launchDataLoad ({
                val list = repository.getGitCommits(repositoryOwner, repositoryName)
                _gitCommitsList.postValue(list)
            }, {
                // do nothing because error condition
                Timber.i("called get list error")
            })
    }

    fun clearGitCommitsList() {
        _gitCommitsList.postValue(emptyList())
    }

    private fun launchDataLoad(complete: suspend () -> Unit, catch: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _showSpinner.value = true
                complete()
            } catch (error: NetworkRequestError) {
                catch()
                _showSnackBar.value = error.message
                Timber.i(error.cause.toString())
            } finally {
                _showSpinner.value = false
            }
        }
    }

}