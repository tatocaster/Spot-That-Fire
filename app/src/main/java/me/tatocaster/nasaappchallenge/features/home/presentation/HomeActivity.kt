package me.tatocaster.nasaappchallenge.features.home.presentation

import android.os.Bundle
import com.mikepenz.materialdrawer.DrawerBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.view.*
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.CustomDrawerPrimaryItem
import me.tatocaster.nasaappchallenge.common.utils.addFragmentToActivity
import me.tatocaster.nasaappchallenge.common.utils.showInfoAlert
import me.tatocaster.nasaappchallenge.features.about.presentation.AboutFragment
import me.tatocaster.nasaappchallenge.features.base.BaseActivity
import me.tatocaster.nasaappchallenge.features.create.presentation.CreateFragment
import timber.log.Timber


class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            // navigate to create activity
            addFragmentToActivity(supportFragmentManager, CreateFragment.newInstance(), R.id.container)
        }

        setUpNavigationDrawer()

        addFragmentToActivity(supportFragmentManager, HomeFragment.newInstance(), R.id.container)
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
                        2 -> {
                            addFragmentToActivity(supportFragmentManager, AboutFragment.newInstance(), R.id.container)
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

}