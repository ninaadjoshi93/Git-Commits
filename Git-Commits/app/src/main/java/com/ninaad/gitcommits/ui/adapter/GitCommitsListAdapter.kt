package com.ninaad.gitcommits.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.databinding.ListItemGitCommitBinding
import com.ninaad.gitcommits.model.GitResponseItem
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by ninaad on 2/25/19.
 */
class GitCommitsListAdapter : RecyclerView.Adapter<GitCommitsListAdapter.GitCommitsListViewHolder?>() {
    private val dateFormatter: SimpleDateFormat
    private val parser: SimpleDateFormat
    private var gitCommitEntryList: List<GitResponseItem> = emptyList()
    init {
        val fromPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val toPattern = "h:mm:ss a MM-dd-yyyy"
        parser = SimpleDateFormat(fromPattern, Locale.getDefault())
        dateFormatter = SimpleDateFormat(toPattern, Locale.getDefault())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitCommitsListViewHolder {
        val binding: ListItemGitCommitBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_git_commit,
            parent, false
        )
        return GitCommitsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitCommitsListViewHolder, i: Int) {
        val gitEntry = gitCommitEntryList[i]
        holder.binding.gitentry = gitEntry
        gitEntry?.author?.avatarUrl?.let { urlString ->
            Picasso.get()
                .load(urlString)
                .error(R.drawable.github_icon)
                .into(holder.binding.authorAvatarIv)
        }
        gitEntry?.commit?.author?.date?.let {
            holder.binding.commitDateTv.text = dateFormatter.format(parser.parse(it))
        }
    }

    override fun getItemCount(): Int {
        return gitCommitEntryList.size
    }

    fun setPullRequestsList(gitCommitsList: List<GitResponseItem>) {
        if (gitCommitsList.isNotEmpty()) {
            gitCommitEntryList = gitCommitsList
        }
        notifyDataSetChanged()
    }

    inner class GitCommitsListViewHolder(
        val binding: ListItemGitCommitBinding
    ) : RecyclerView.ViewHolder(binding.root)
}