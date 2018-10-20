package me.tatocaster.nasaappchallenge.features.home.presentation

import dagger.Module
import dagger.Provides

@Module
class HomeActivityModule {

}

@Module
class HomeFragmentModule {
    @Provides
    fun providesView(view: HomeFragment): HomeContract.View = view

    @Provides
    fun providesPresenter(presenter: HomePresenter): HomeContract.Presenter = presenter
}