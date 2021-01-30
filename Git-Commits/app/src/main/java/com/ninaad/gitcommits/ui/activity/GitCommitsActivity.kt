package com.ninaad.gitcommits.ui.activity

import android.os.Bundle
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.ui.fragment.GitCommitsFragment
import com.ninaad.gitcommits.ui.fragment.GitLandingFragment
import com.ninaad.gitcommits.viewmodel.ShowGitCommitsInterface
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


class GitCommitsActivity : DaggerAppCompatActivity(), ShowGitCommitsInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_commits)

        supportFragmentManager.beginTransaction()
            .replace(R.id.git_commits_fragment, GitLandingFragment(), "current")
            .commit()
    }

    override fun showGitCommitsFragment() {
        Timber.i("called showGitCommitsFragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.git_commits_fragment, GitCommitsFragment(), "current")
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("current")
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.git_commits_fragment, GitLandingFragment())
                .commit()
        } else {
            super.onBackPressed()
        }
    }
}