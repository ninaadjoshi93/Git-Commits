package com.ninaad.gitcommits.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.databinding.FragmentGitCommitsBinding
import com.ninaad.gitcommits.ui.adapter.GitCommitsListAdapter
import com.ninaad.gitcommits.viewmodel.GitCommitsViewModel
import dagger.android.support.DaggerFragment

class GitCommitsFragment: DaggerFragment() {

    lateinit var viewModel: GitCommitsViewModel

    private lateinit var gitCommitsListBinding: FragmentGitCommitsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gitCommitsListBinding = FragmentGitCommitsBinding.inflate(layoutInflater)
        return gitCommitsListBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(GitCommitsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
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
            (adapter as GitCommitsListAdapter).setPullRequestsList(emptyList())
        }
    }
}