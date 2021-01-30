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
import com.ninaad.gitcommits.viewmodel.ShowGitCommitsInterface
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class GitLandingFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: GitCommitsViewModel

    private lateinit var fragmentLandingBinding: FragmentLandingBinding

    private lateinit var listener: ShowGitCommitsInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLandingBinding = FragmentLandingBinding.inflate(inflater)
        return fragmentLandingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = context as ShowGitCommitsInterface
        setupSnackBar()
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

    private fun setupSnackBar() {
        viewModel.snackBar.observe(viewLifecycleOwner) { text ->
            text?.let {
                showSnackBarWithDescription(it)
            }
        }
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
        viewModel.onSnackBarShown()
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
        Timber.i("called onShowGitCommitsListButtonClicked")
        if (viewModel.isNetworkAvailable(requireContext())) {
            viewModel.updateRepositoryOwner(owner)
            viewModel.updateRepositoryName(name)
            viewModel.onGetGitCommitsListButtonClick()
            if (owner.trim().isNotEmpty() && name.trim().isNotEmpty()) {
                populateCommitsList()
                moveToCommitsListFragment()
            } else {
                Timber.i("called incomplete fields")
            }
        } else {
            showSnackBarWithDescription(getString(R.string.network_not_available))
        }
    }
    private fun populateCommitsList() {
        viewModel.getGitCommitsList()
    }

    private fun moveToCommitsListFragment() {
        viewModel.gitCommitsList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                listener.showGitCommitsFragment()
            }
        }
    }
}