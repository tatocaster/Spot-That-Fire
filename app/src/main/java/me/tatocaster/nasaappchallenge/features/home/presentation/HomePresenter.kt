package me.tatocaster.nasaappchallenge.features.home.presentation

import io.reactivex.subscribers.ResourceSubscriber
import me.tatocaster.nasaappchallenge.entity.WildFireActivity
import me.tatocaster.nasaappchallenge.features.home.usecase.HomeUseCase
import javax.inject.Inject

class HomePresenter @Inject constructor(private var useCase: HomeUseCase,
                                        private var view: HomeContract.View) : HomeContract.Presenter {
    override fun getWildfires() {
        val subscriber = object : ResourceSubscriber<MutableList<WildFireActivity>>() {
            override fun onNext(t: MutableList<WildFireActivity>) {
                view.updateList(t)
            }

            override fun onError(t: Throwable) {
                view.showError(t.message!!)
            }

            override fun onComplete() {

            }
        }
        useCase.execute("", subscriber)
    }

    override fun detach() {
        useCase.clear()
    }

}