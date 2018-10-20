package me.tatocaster.nasaappchallenge.features.create.presentation

interface CreateContract {
    interface View {
        fun showError(message: String)
    }

    interface Presenter {
        fun detach()
    }
}