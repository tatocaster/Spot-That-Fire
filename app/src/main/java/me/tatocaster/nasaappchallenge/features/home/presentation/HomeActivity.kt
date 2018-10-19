package me.tatocaster.nasaappchallenge.features.home.presentation

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.mikepenz.materialdrawer.DrawerBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header.view.*
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.CustomDrawerPrimaryItem
import me.tatocaster.nasaappchallenge.common.utils.SeparatorItemDecoration
import me.tatocaster.nasaappchallenge.common.utils.showErrorAlert
import me.tatocaster.nasaappchallenge.common.utils.showInfoAlert
import me.tatocaster.nasaappchallenge.entity.WildFireActivity
import me.tatocaster.nasaappchallenge.features.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : BaseActivity(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    private lateinit var adapter: WildfiresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        adapter = WildfiresAdapter(this) {
            val bundle = Bundle().apply {
                //                putParcelable("strava_activity", it)
            }
            /*val intent = Intent(this, CreateActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)*/
        }

        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        activitiesRV.addItemDecoration(SeparatorItemDecoration(this, ContextCompat.getColor(this, R.color.colorPrimaryDark), 1f))
        activitiesRV.layoutManager = layoutManager
        activitiesRV.setHasFixedSize(true)
        activitiesRV.adapter = adapter

        fab.setOnClickListener {
            // navigate to create activity
        }

        setUpNavigationDrawer()
    }

    private fun setUpNavigationDrawer() {
        val header = layoutInflater.inflate(R.layout.nav_header, null, false)

        val drawerBuilder = DrawerBuilder()
                .withActivity(this)
                .withHeader(header)
                .withToolbar(toolbar)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    if (drawerItem == null) {
                        return@withOnDrawerItemClickListener true
                    }
                    when (drawerItem.identifier.toInt()) {
                        1 -> {
                            Timber.d("clicked item :  %s", drawerItem.identifier)
                        }
                    }
                    false
                }

        drawerBuilder
                .addDrawerItems(
                        CustomDrawerPrimaryItem.newPrimaryItem(this, 1, "Help"),
                        CustomDrawerPrimaryItem.newPrimaryItem(this, 2, "About Us").withHiddenDivider()
                )

        header.userLoginTextView.setOnClickListener {
            showInfoAlert(this, "", "Temporarily Disabled")
        }

        drawerBuilder.build()
    }

    override fun onResume() {
        super.onResume()

//        presenter.getWildfires()
    }

    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun updateList(data: MutableList<WildFireActivity>) {

    }
}