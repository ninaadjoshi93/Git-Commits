package com.ninaad.gitcommits.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.databinding.FragmentLandingBinding
import com.ninaad.gitcommits.viewmodel.GitCommitsViewModel
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class GitLandingFragment constructor(private val showListFragment: () -> Unit) : DaggerFragment() {

    @Inject
    lateinit var viewModel: GitCommitsViewModel

    private lateinit var fragmentLandingBinding: FragmentLandingBinding

    companion object {
        fun newInstance(showListFragment: () -> Unit): GitLandingFragment {
            return GitLandingFragment(showListFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLandingBinding = FragmentLandingBinding.inflate(inflater)
        return fragmentLandingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is GitCommitsViewModel.UIStates.Success -> {
                    // Update RecyclerView
                    // Hide ProgressBar
                    fragmentLandingBinding.spinner.visibility = View.GONE
                    if (uiState.list.isNotEmpty()) {
                        showListFragment()
                    }
                }
                is GitCommitsViewModel.UIStates.InProgress ->  {
                    // Show ProgressBar
                    fragmentLandingBinding.spinner.visibility = View.VISIBLE
                }
                is GitCommitsViewModel.UIStates.EmptyOwner -> {
                    // Hide Progress bar
                    // Show error with empty owner
                    fragmentLandingBinding.spinner.visibility = View.GONE
                    showSnackBarWithDescription(getString(R.string.provide_repo_owner))
                }
                is GitCommitsViewModel.UIStates.EmptyName -> {
                    // Hide Progress bar
                    // Show error with empty name
                    fragmentLandingBinding.spinner.visibility = View.GONE
                    showSnackBarWithDescription(getString(R.string.provide_repo_name))
                }
                is GitCommitsViewModel.UIStates.TimeOut -> {
                    // Hide Progress bar
                    // Show error with timeout and retry
                    fragmentLandingBinding.spinner.visibility = View.GONE
                    showSnackBarWithDescription(getString(R.string.request_timed_out))
                }
                is GitCommitsViewModel.UIStates.Error -> {
                    // Show error with message
                    fragmentLandingBinding.spinner.visibility = View.GONE
                    showSnackBarWithDescription(uiState.errorString)
                }
                is GitCommitsViewModel.UIStates.NoNetwork -> {
                    // Show no network message
                    showSnackBarWithDescription(getString(R.string.network_not_available))
                }
                is GitCommitsViewModel.UIStates.Idle -> {
                    // Idle state
                    fragmentLandingBinding.spinner.visibility = View.GONE
                }
            }
        }
        setupButtons()
        setupEditTexts()
    }

    override fun onPause() {
        super.onPause()
        disableButtons()
    }

    override fun onResume() {
        super.onResume()
        enableButtons()
    }

    private fun setupButtons() {
        fragmentLandingBinding.goToCommitsListBtn.setOnClickListener {
            val owner = fragmentLandingBinding.repositoryOwnerEt.text.toString()
            val name = fragmentLandingBinding.repositoryNameEt.text.toString()
            onShowGitCommitsListButtonClicked(owner, name)
        }
        fragmentLandingBinding.selectMyRepositoryBtn.setOnClickListener {
            fragmentLandingBinding.repositoryOwnerEt.setText(getString(R.string.sample_repository_owner))
            fragmentLandingBinding.repositoryNameEt.setText(getString(R.string.sample_repository_name))
            onShowGitCommitsListButtonClicked()
        }
    }

    private fun setupEditTexts() {
        fragmentLandingBinding.repositoryOwnerEt.setText(viewModel.getRepositoryOwner())
        fragmentLandingBinding.repositoryNameEt.setText(viewModel.getRepositoryName())
    }

    private fun showSnackBarWithDescription(message: String) {
        Snackbar
            .make(fragmentLandingBinding.root, message, Snackbar.LENGTH_SHORT)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                    disableButtons()
                }
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    enableButtons()
                }
            })
            .show()
    }

    private fun disableButtons() {
        fragmentLandingBinding.goToCommitsListBtn.isEnabled = false
        fragmentLandingBinding.selectMyRepositoryBtn.isEnabled = false
    }

    private fun enableButtons() {
        fragmentLandingBinding.goToCommitsListBtn.isEnabled = true
        fragmentLandingBinding.selectMyRepositoryBtn.isEnabled = true
    }

    private fun onShowGitCommitsListButtonClicked(
        owner: String = getString(R.string.sample_repository_owner),
        name: String = getString(R.string.sample_repository_name)
    ) {
        viewModel.updateRepositoryOwner(owner)
        viewModel.updateRepositoryName(name)
        viewModel.onGetGitCommitsListButtonClick()
    }
}