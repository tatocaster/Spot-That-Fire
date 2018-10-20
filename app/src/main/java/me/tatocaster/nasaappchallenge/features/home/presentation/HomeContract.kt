package me.tatocaster.nasaappchallenge.features.home.presentation

import com.google.firebase.firestore.DocumentSnapshot

interface HomeContract {
    interface View {
        fun showError(message: String)

        fun updateList(data: MutableList<DocumentSnapshot>)
    }

    interface Presenter {
        fun detach()

        fun getWildfires()
    }
}