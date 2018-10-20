package me.tatocaster.nasaappchallenge.features.home.presentation

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import me.tatocaster.nasaappchallenge.features.home.usecase.HomeUseCase
import javax.inject.Inject


class HomePresenter @Inject constructor(private var useCase: HomeUseCase,
                                        private var view: HomeContract.View) : HomeContract.Presenter {
    override fun getWildfires() {
        val db = FirebaseFirestore.getInstance()
        db.collection("posts")
                .orderBy("created_at", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        for (document in task.result!!) {
                        view.updateList(task.result!!.documents)
//                        }
                    } else {
                        view.showError("Error!")
                    }
                }
    }

    override fun detach() {
        useCase.clear()
    }

}