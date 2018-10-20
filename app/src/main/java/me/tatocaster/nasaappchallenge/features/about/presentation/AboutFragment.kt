package me.tatocaster.nasaappchallenge.features.about.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.tatocaster.nasaappchallenge.BuildConfig
import me.tatocaster.nasaappchallenge.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AboutPage(activity)
                .isRTL(false)
                .setDescription(getString(R.string.about_description))
                .setImage(R.drawable.ic_fire)
                .addItem(Element(getString(R.string.version) + " " + BuildConfig.VERSION_NAME, null))
                .addGroup(getString(R.string.connect_with_us))
                .addEmail("kutaliatato@gmail.com")
                .addWebsite("http://spotthefire.surge.sh/")
                .addPlayStore("me.tatocaster.spotthatfire")
                .addGitHub("tatocaster")
                .create()
    }

    companion object {
        val TAG = "AboutFragment"

        @JvmStatic
        fun newInstance(): AboutFragment = AboutFragment()
    }
}