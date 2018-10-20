package me.tatocaster.nasaappchallenge.features.create.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.showErrorAlert
import me.tatocaster.nasaappchallenge.features.base.BaseFragment
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import javax.inject.Inject

class CreateFragment : BaseFragment(), CreateContract.View {
    @Inject
    lateinit var presenter: CreateContract.Presenter

    private lateinit var homeActivity: HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create, container, false)

        homeActivity = activity as HomeActivity

        return root
    }

    override fun showError(message: String) {
        showErrorAlert(homeActivity, "", message)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    companion object {
        @JvmStatic
        fun newInstance(): CreateFragment = CreateFragment()
    }

}