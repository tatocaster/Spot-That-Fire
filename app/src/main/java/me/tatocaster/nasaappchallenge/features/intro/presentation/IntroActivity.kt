package me.tatocaster.nasaappchallenge.features.intro.presentation

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import me.tatocaster.nasaappchallenge.INTRO_PASSED
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.utils.PreferenceHelper
import me.tatocaster.nasaappchallenge.common.utils.PreferenceHelper.get
import me.tatocaster.nasaappchallenge.common.utils.PreferenceHelper.set
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity


class IntroActivity : AppIntro2() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = PreferenceHelper.defaultPrefs(this)

        if (prefs[INTRO_PASSED, false] != false) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        setDepthAnimation()
        addSlides()
        skipButtonEnabled = false

        askPermissions()

        /*FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful()) {
                        Timber.e(task.getException())
                        return@FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.getResult().getToken()
                    Timber.d("login activity, token %s", token)

                    if (getStringPreference(FCM_DEVICE_ID).equals(token))
                        return@FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener

                    prefs[FCM_DEVICE_ID] = token
                }*/
    }

    private fun addSlides() {
        addSlide(AppIntro2Fragment.newInstance("Spot The Fire", "Awesome Description", R.drawable.ic_launcher_background,
                ContextCompat.getColor(this, R.color.colorPrimary)))

        addSlide(AppIntro2Fragment.newInstance("Spot The Fire", "Awesome Description 2", R.drawable.ic_launcher_background,
                ContextCompat.getColor(this, R.color.colorPrimary)))
    }

    private fun askPermissions() {
        askForPermissions(arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        prefs[INTRO_PASSED] = true
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}
