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

    private var _repositoryOwner: String = ""
    private var _repositoryName: String = ""


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

    fun updateRepositoryOwner(owner: String){
        _repositoryOwner = owner
        Timber.i("value of = $_repositoryOwner")
    }

    fun updateRepositoryName(name: String){
        _repositoryName = name
        Timber.i("value of = $_repositoryName")
    }

    fun onGetGitCommitsListButtonClick() {
        if (_repositoryOwner.trim().isEmpty()) {
            _showSnackBar.value = "Please Enter Github Repository Owner"
            return
        }
        if (_repositoryName.trim().isEmpty()) {
            _showSnackBar.value = "Please Enter Github Repository Name"
            return
        }
    }

    fun getGitCommitsList() {
        launchDataLoad ({
                val list = repository.getGitCommits(_repositoryOwner, _repositoryName)
                _gitCommitsList.postValue(list)
            }, {
                _gitCommitsList.postValue(emptyList())
            }
        )
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

    override fun onCleared() {
        super.onCleared()
    }

}