package me.tatocaster.nasaappchallenge.features.create.presentation

import dagger.Module
import dagger.Provides


@Module
class CreateFragmentModule {
    @Provides
    fun providesView(view: CreateFragment): CreateContract.View = view

    @Provides
    fun providesPresenter(presenter: CreatePresenter): CreateContract.Presenter = presenter
}