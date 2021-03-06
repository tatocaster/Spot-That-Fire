package me.tatocaster.nasaappchallenge.features.home.presentation

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.fragment_home.view.*
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.SeparatorItemDecoration
import me.tatocaster.nasaappchallenge.common.utils.showErrorAlert
import me.tatocaster.nasaappchallenge.entity.WildFireActivity
import me.tatocaster.nasaappchallenge.features.base.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeContract.View {
    @Inject
    lateinit var presenter: HomeContract.Presenter

    private lateinit var adapter: WildfiresAdapter
    private lateinit var homeActivity: HomeActivity
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        homeActivity = activity as HomeActivity

        adapter = WildfiresAdapter(homeActivity) {
            val bundle = Bundle().apply {
                //                putParcelable("wildfire_activity", it)
            }
            /*val intent = Intent(this, CreateActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)*/
        }

        val layoutManager = LinearLayoutManager(homeActivity)
        root.activitiesRV.addItemDecoration(SeparatorItemDecoration(homeActivity, ContextCompat.getColor(homeActivity, R.color.colorPrimaryDark), 1f))
        root.activitiesRV.layoutManager = layoutManager
        root.activitiesRV.setHasFixedSize(true)
        root.activitiesRV.adapter = adapter

        root.fab.setOnClickListener {
            // navigate to create activity
            homeActivity.navigateToCreateFragment()
        }

        return root
    }

    override fun showError(message: String) {
        showErrorAlert(homeActivity, "", message)
    }

    override fun updateList(data: MutableList<DocumentSnapshot>) {
        root.progressBar.visibility = View.GONE
        root.activitiesRV.visibility = View.VISIBLE

        val wildfires = mutableListOf<WildFireActivity>()

        for (document in data) {
            wildfires.add(WildFireActivity(
                    document.getString("description"),
                    document.getString("image"),
                    document.getDouble("lat"),
                    document.getDouble("lng"),
                    document.getString("created_at"),
                    document.getString("weather")
            ))
        }

        adapter.setActivities(wildfires)
    }


    override fun onResume() {
        super.onResume()
        presenter.getWildfires()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment = HomeFragment()
    }

}