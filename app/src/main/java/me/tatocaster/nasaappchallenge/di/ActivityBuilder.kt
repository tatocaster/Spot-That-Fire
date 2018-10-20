package me.tatocaster.nasaappchallenge.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.tatocaster.nasaappchallenge.features.base.BaseActivity
import me.tatocaster.nasaappchallenge.features.base.BaseActivityModule
import me.tatocaster.nasaappchallenge.features.base.BaseFragment
import me.tatocaster.nasaappchallenge.features.base.BaseFragmentModule
import me.tatocaster.nasaappchallenge.features.create.presentation.CreateFragment
import me.tatocaster.nasaappchallenge.features.create.presentation.CreateFragmentModule
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivityModule
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeFragment
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeFragmentModule
import me.tatocaster.nasaappchallenge.features.map.presentation.MapsActivity
import me.tatocaster.nasaappchallenge.features.map.presentation.MapsActivityModule

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [MapsActivityModule::class])
    abstract fun bindMapsActivity(): MapsActivity

    @ContributesAndroidInjector(modules = [BaseActivityModule::class])
    abstract fun bindBaseActivity(): BaseActivity



    // Fragments
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [BaseFragmentModule::class])
    abstract fun bindBaseFragment(): BaseFragment

    @ContributesAndroidInjector(modules = [CreateFragmentModule::class])
    abstract fun bindCreateFragment(): CreateFragment
}
