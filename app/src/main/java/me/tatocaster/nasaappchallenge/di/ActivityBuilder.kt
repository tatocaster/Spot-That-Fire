package me.tatocaster.nasaappchallenge.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.tatocaster.nasaappchallenge.features.base.BaseActivity
import me.tatocaster.nasaappchallenge.features.base.BaseActivityModule
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivityModule

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [BaseActivityModule::class])
    abstract fun bindBaseActivity(): BaseActivity
}
