package com.ninaad.gitcommits.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Convenience factory to handle ViewModels with two parameters.
 *
 * Make a factory:
 * ```
 * // Make a factory
 * val FACTORY = viewModelFactory(::MyViewModel)
 * ```
 *
 * Use the generated factory:
 * ```
 * ViewModelProviders.of(this, FACTORY(argument1, argument2))
 *
 * ```
 *
 * @param constructor A function (A, B) -> T that returns an instance of the ViewModel (typically pass
 * the constructor)
 * @return a function of two arguments that returns ViewModelProvider.Factory for ViewModelProviders
 */
fun <T : ViewModel, A, B> dualArgViewModelFactory(constructor: (A, B) -> T):
        (A, B) -> ViewModelProvider.NewInstanceFactory {
    return { argv1: A, argv2: B ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(argv1, argv2) as V
            }
        }
    }
}