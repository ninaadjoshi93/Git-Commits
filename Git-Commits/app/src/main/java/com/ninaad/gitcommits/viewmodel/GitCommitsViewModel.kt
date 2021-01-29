package com.ninaad.gitcommits.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninaad.gitcommits.model.GitResponseItem
import com.ninaad.gitcommits.repository.GitCommitsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class GitCommitsViewModel(private val repository: GitCommitsRepository) : ViewModel() {

    companion object {
        /**
         * Factory for creating [GitCommitsViewModel]
         *
         * @param arg the repository to pass to [GitCommitsViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::GitCommitsViewModel)
    }

    private val gitCommitsList = MutableLiveData<List<GitResponseItem>>()


    val commitsList: LiveData<List<GitResponseItem>>
        get() = gitCommitsList



    fun getGitCommitsList() {
        viewModelScope.launch {
            Timber.i("called getGitCommitList")
            val list = repository.getGitCommits("a", "b")
            for (i in list) {
                Timber.i("called list item $i")
            }
            gitCommitsList.postValue(list)
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}