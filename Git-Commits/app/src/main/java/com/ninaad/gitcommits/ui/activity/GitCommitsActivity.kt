package com.ninaad.gitcommits.ui.activity

import android.os.Bundle
import com.ninaad.gitcommits.R
import dagger.android.support.DaggerAppCompatActivity

class GitCommitsActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_commits)
    }
}