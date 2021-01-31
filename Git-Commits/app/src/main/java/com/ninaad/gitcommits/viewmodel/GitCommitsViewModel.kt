package com.ninaad.gitcommits.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninaad.gitcommits.model.GitResponseItem
import com.ninaad.gitcommits.repository.GitCommitsRepository
import com.ninaad.gitcommits.util.NetworkUtil
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitCommitsViewModel @Inject constructor(private val repository: GitCommitsRepository, private val networkUtil: NetworkUtil) : ViewModel() {

    companion object {
        val FACTORY = dualArgViewModelFactory(::GitCommitsViewModel)
    }

    private var repositoryOwner: String = ""
    private var repositoryName: String = ""

    private val _uiState = MutableLiveData<UIStates>()
    val uiState: LiveData<UIStates>
        get() = _uiState

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
            _uiState.postValue(UIStates.EmptyOwner)
            return
        }
        if (repositoryName.trim().isEmpty()) {
            _uiState.postValue(UIStates.EmptyName)
            return
        }
    }

    fun getGitCommitsList() = viewModelScope.launch {
        try {
            _uiState.postValue(UIStates.InProgress)
            val data = repository.getGitCommits(repositoryOwner, repositoryName)
            _uiState.postValue(UIStates.Success(data))
        } catch (timeout: TimeoutCancellationException) {
            _uiState.postValue(UIStates.TimeOut)
        } catch (network: IOException) {
            _uiState.postValue(UIStates.Error(network.message ?: "Unknown Error"))
        } catch (httpException: HttpException) {
            _uiState.postValue(UIStates.Error(httpException.message ?: "Unknown Error"))
        }
        finally {
            delay(1000)
            _uiState.postValue(UIStates.Idle)
        }
    }

    sealed class UIStates {
        data class Success(val list: List<GitResponseItem>): UIStates()
        object InProgress: UIStates()
        object TimeOut: UIStates()
        data class Error(val errorString: String): UIStates()
        object EmptyOwner: UIStates()
        object EmptyName: UIStates()
        object Idle: UIStates()
    }

}