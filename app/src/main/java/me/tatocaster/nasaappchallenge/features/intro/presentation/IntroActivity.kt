package me.tatocaster.nasaappchallenge.features.intro.presentation

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import me.tatocaster.nasaappchallenge.INTRO_PASSED
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.common.AppFirebaseMessagingService.Companion.FCM_DEVICE_ID
import me.tatocaster.nasaappchallenge.common.utils.PreferenceHelper
import me.tatocaster.nasaappchallenge.common.utils.PreferenceHelper.get
import me.tatocaster.nasaappchallenge.common.utils.PreferenceHelper.set
import me.tatocaster.nasaappchallenge.features.home.presentation.HomeActivity
import timber.log.Timber


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

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Timber.e(task.exception)
                        return@addOnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    Timber.d("login activity, token %s", token)

                    if (prefs[FCM_DEVICE_ID, ""].equals(token))
                        return@addOnCompleteListener

                    prefs[FCM_DEVICE_ID] = token

                    val db = FirebaseFirestore.getInstance()
                    db.collection("device_ids")
                            .add(hashMapOf(Pair("device_id", token!!)) as Map<String, Any>)
                            .addOnSuccessListener { documentReference ->
                                Timber.d("DocumentSnapshot added with ID: %s", documentReference.id)
                            }
                            .addOnFailureListener { e -> Timber.e(e) }
                }
    }

    private fun addSlides() {
        addSlide(AppIntro2Fragment.newInstance("Spot That Fire", getString(R.string.intro_1_desc), R.drawable.ic_earth,
                ContextCompat.getColor(this, R.color.colorPrimary)))

        addSlide(AppIntro2Fragment.newInstance("Spot That Fire", getString(R.string.intro_2_desc), R.drawable.ic_fire,
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
