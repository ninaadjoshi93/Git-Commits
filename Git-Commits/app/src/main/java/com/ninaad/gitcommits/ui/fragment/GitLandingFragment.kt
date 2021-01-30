package com.ninaad.gitcommits.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.ninaad.gitcommits.databinding.FragmentLandingBinding
import com.ninaad.gitcommits.repository.GitCommitsRepository
import com.ninaad.gitcommits.viewmodel.GitCommitsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class GitLandingFragment : DaggerFragment() {


    private lateinit var fragmentLandingBinding: FragmentLandingBinding

    @Inject
    lateinit var viewModel: GitCommitsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLandingBinding = FragmentLandingBinding.inflate(inflater)
        return fragmentLandingBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.snackbar.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBarShown()
            }
        }
    }

//    protected fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        gitRequestsViewModel.init()
//        if (GDUtilities.isNetworkAvailable(this)) {
//            gitRequestsViewModel.getGitQuote().observe(this, { mQuote ->
//                if (null != mQuote && mQuote.length() > 0) {
//                    activityLandingBinding.loadingQuotePb.setVisibility(View.GONE)
//                    activityLandingBinding.landingScreenRl.setVisibility(View.VISIBLE)
//                    activityLandingBinding.setGitQuote(mQuote)
//                } else {
//                    activityLandingBinding.loadingQuotePb.setVisibility(View.GONE)
//                    activityLandingBinding.landingScreenRl.setVisibility(View.VISIBLE)
//                    activityLandingBinding.setGitQuote(getString(R.string.no_internet))
//                }
//            })
//        } else {
//            activityLandingBinding.loadingQuotePb.setVisibility(View.GONE)
//            activityLandingBinding.landingScreenRl.setVisibility(View.VISIBLE)
//            activityLandingBinding.setGitQuote(getString(R.string.no_internet))
//        }
//    }

//    fun goToYourRepository(view: View) {
//        if (!GDUtilities.isNetworkAvailable(this)) {
//            return
//        }
//        activityLandingBinding.goToPullRequestsListBtn.setEnabled(false)
//        activityLandingBinding.selectMyRepositoryBtn.setEnabled(false)
//        val mOwnerNameAndRepo: Array<String> = activityLandingBinding.repositoryUrlEt.getText()
//                .toString().split(", *")
//        val message: String
//        val mRepoOwner: String = getResources().getString(R.string.repo_owner)
//        val mRepoName: String = getResources().getString(R.string.repo_name)
//
//        // if you provide valid owner and repository names
//        if (mOwnerNameAndRepo.size == 2) {
//            // if you provide me pull requests to my favorite repository
//            message = if (mOwnerNameAndRepo[0] == mRepoOwner && mOwnerNameAndRepo[1] == mRepoName) {
//                "Woah! Your repository is the same as my favorite " +
//                        "repository. Let's check it out. :)"
//            } else {
//                "Cool. Let's check your repository. :)"
//            }
//            val mNameRepoSet = mOwnerNameAndRepo[0] + ", " + mOwnerNameAndRepo[1]
//            activityLandingBinding.repositoryUrlEt.setText(mNameRepoSet)
//            activityLandingBinding.setGitRepository(GDGitRepository(mOwnerNameAndRepo[0],
//                    mOwnerNameAndRepo[1]))
//        } else {
//            activityLandingBinding.setGitRepository(GDGitRepository(mRepoOwner, mRepoName))
//            message = "Oops! Your repository is not valid. I will " +
//                    "show you my favorite repository instead. :)"
//            val mNameRepoSet = "$mRepoOwner, $mRepoName"
//            activityLandingBinding.repositoryUrlEt.setText(mNameRepoSet)
//        }
//        showSnackBar(view, message, activityLandingBinding.getGitRepository())
//    }

//    fun goToMyRepository(view: View) {
//        if (!GDUtilities.isNetworkAvailable(this)) {
//            return
//        }
//        activityLandingBinding.goToPullRequestsListBtn.setEnabled(false)
//        activityLandingBinding.selectMyRepositoryBtn.setEnabled(false)
//        val message = "Hooray! Check out my favorite " +
//                "repository. :)"
//        val mRepoOwner: String = getResources().getString(R.string.repo_owner)
//        val mRepoName: String = getResources().getString(R.string.repo_name)
//        val mNameRepoSet = "$mRepoOwner, $mRepoName"
//        activityLandingBinding.repositoryUrlEt.setText(mNameRepoSet)
//        val gitRepository = GDGitRepository(mRepoOwner, mRepoName)
//        showSnackBar(view, message, gitRepository)
//    }

//    protected fun onResume() {
//        super.onResume()
//        activityLandingBinding.goToPullRequestsListBtn.setEnabled(true)
//        activityLandingBinding.selectMyRepositoryBtn.setEnabled(true)
//    }
}