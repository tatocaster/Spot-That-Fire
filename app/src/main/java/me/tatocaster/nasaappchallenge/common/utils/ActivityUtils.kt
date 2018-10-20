package me.tatocaster.nasaappchallenge.common.utils

import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.addFragmentToActivity(@NonNull fragmentManager: FragmentManager,
                                            @NonNull fragment: Fragment, frameId: Int) {
    checkNotNull(fragmentManager)
    checkNotNull(fragment)
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(frameId, fragment).addToBackStack(null).commit()
}