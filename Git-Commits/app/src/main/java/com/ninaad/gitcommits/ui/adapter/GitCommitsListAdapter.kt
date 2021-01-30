package com.ninaad.gitcommits.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.databinding.ListItemGitCommitBinding
import com.ninaad.gitcommits.model.GitResponseItem
import com.squareup.picasso.Picasso

/**
 * Created by ninaad on 2/25/19.
 */
class GitCommitsListAdapter : RecyclerView.Adapter<GitCommitsListAdapter.GitCommitsListViewHolder?>() {
    private var gitCommitEntryList: List<GitResponseItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitCommitsListViewHolder {
        val binding: ListItemGitCommitBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_git_commit,
            parent, false
        )
        return GitCommitsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitCommitsListViewHolder, i: Int) {
        holder.binding.gitentry = gitCommitEntryList[i]
        Picasso.get().load(gitCommitEntryList[i].author.avatarUrl).into(holder.binding.authorAvatarIv)
    }

    override fun getItemCount(): Int {
        return gitCommitEntryList.size
    }

    fun setPullRequestsList(gitCommitsList: List<GitResponseItem>) {
        gitCommitEntryList = gitCommitsList
        notifyDataSetChanged()
    }

    inner class GitCommitsListViewHolder(
        val binding: ListItemGitCommitBinding
        ) : RecyclerView.ViewHolder(binding.root)
}