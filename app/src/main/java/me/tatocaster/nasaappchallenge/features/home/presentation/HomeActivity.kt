package me.tatocaster.nasaappchallenge.features.home.presentation

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
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

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)


        auth = FirebaseAuth.getInstance()

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
                        99 -> {
                            addFragmentToActivity(supportFragmentManager, HomeFragment.newInstance(), R.id.container)
                        }
                    }
                    false
                }

        drawerBuilder
                .addDrawerItems(
                        CustomDrawerPrimaryItem.newPrimaryItem(this, 99, "Home").withHiddenDivider(),
                        DividerDrawerItem(),
                        CustomDrawerPrimaryItem.newPrimaryItem(this, 1, "Help"),
                        CustomDrawerPrimaryItem.newPrimaryItem(this, 2, "About Us").withHiddenDivider()
                )

        header.userLoginTextView.setOnClickListener {
            showInfoAlert(this, "", "Temporarily Disabled")
        }

        drawerBuilder.build()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
        } else {
            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                        } else {
                        }
                    }
        }
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount != 1 -> {
                supportFragmentManager.popBackStack()
                fab.visibility = View.GONE
            }
            else -> super.onBackPressed()
        }
    }

}