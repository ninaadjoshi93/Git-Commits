package com.ninaad.gitcommits.ui.activity

import android.os.Bundle
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.ui.fragment.GitCommitsFragment
import com.ninaad.gitcommits.ui.fragment.GitLandingFragment
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


class GitCommitsActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_commits)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.git_commits_fragment, GitLandingFragment.newInstance {
                    showGitCommitsFragment()
                }, "landing")
                .commit()
        }
    }

    private fun showGitCommitsFragment() {
        Timber.i("called showGitCommitsFragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.git_commits_fragment, GitCommitsFragment.newInstance(), "current")
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag("current")
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.git_commits_fragment, GitLandingFragment.newInstance {
                    showGitCommitsFragment()
                })
                .commit()
        } else {
            super.onBackPressed()
        }
    }
}