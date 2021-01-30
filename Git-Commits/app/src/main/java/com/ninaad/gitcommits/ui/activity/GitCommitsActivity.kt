package com.ninaad.gitcommits.ui.activity

import android.os.Bundle
import com.ninaad.gitcommits.R
import com.ninaad.gitcommits.ui.fragment.GitCommitsFragment
import com.ninaad.gitcommits.ui.fragment.GitLandingFragment
import dagger.android.support.DaggerAppCompatActivity

class GitCommitsActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_commits)

        supportFragmentManager.beginTransaction()
                .add(R.id.git_commits_fragment, GitCommitsFragment())
                .commit()

//        supportFragmentManager.beginTransaction()
//                .add(R.id.git_commits_fragment, GitLandingFragment())
//                .commit()

    }
}