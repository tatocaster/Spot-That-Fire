package me.tatocaster.nasaappchallenge.features.home.presentation

import me.tatocaster.nasaappchallenge.entity.WildFireActivity

interface HomeContract {
    interface View {
        fun showError(message: String)

        fun updateList(data : MutableList<WildFireActivity>)
    }

    interface Presenter {
        fun detach()

        fun getWildfires()
    }
}