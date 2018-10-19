package me.tatocaster.nasaappchallenge.features.home.presentation

import dagger.Module
import dagger.Provides

@Module
class HomeActivityModule {
    @Provides
    fun providesView(view: HomeActivity): HomeContract.View = view

    @Provides
    fun providesPresenter(presenter: HomePresenter): HomeContract.Presenter = presenter
}