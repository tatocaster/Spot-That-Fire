package me.tatocaster.nasaappchallenge.features.create.presentation

import me.tatocaster.nasaappchallenge.features.home.usecase.HomeUseCase
import javax.inject.Inject

class CreatePresenter @Inject constructor(private var useCase: HomeUseCase,
                                        private var view: CreateContract.View) : CreateContract.Presenter {


    override fun detach() {
        useCase.clear()
    }

}