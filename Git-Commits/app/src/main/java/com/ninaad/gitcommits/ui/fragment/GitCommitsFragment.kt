package com.ninaad.gitcommits.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.databinding.FragmentGitCommitsBinding
import com.ninaad.gitcommits.ui.adapter.GitCommitsListAdapter
import com.ninaad.gitcommits.viewmodel.GitCommitsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GitCommitsFragment: DaggerFragment() {

    @Inject
    lateinit var viewModel: GitCommitsViewModel

    private lateinit var gitCommitsListBinding: FragmentGitCommitsBinding

    companion object {
        fun newInstance(): GitCommitsFragment {
            return GitCommitsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gitCommitsListBinding = FragmentGitCommitsBinding.inflate(layoutInflater)
        return gitCommitsListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is GitCommitsViewModel.UIStates.Success -> {
                    // Update RecyclerView
                    // Hide ProgressBar
                    gitCommitsListBinding.spinner.visibility = View.GONE
                    if (uiState.list.isNotEmpty()) {
                        (gitCommitsListBinding.gitCommitsListRv.adapter as GitCommitsListAdapter).setPullRequestsList(uiState.list)
                    }
                }
                is GitCommitsViewModel.UIStates.InProgress ->  {
                    // Show ProgressBar
                    gitCommitsListBinding.spinner.visibility = View.VISIBLE
                }
                is GitCommitsViewModel.UIStates.Idle -> {
                    // do nothing
                }
            }
        }


    }

    private fun setUpList() {
        with(gitCommitsListBinding.gitCommitsListRv) {
            adapter = GitCommitsListAdapter()
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.list_divider)?.let {
                    setDrawable(it)
                }
            })
        }
    }
}