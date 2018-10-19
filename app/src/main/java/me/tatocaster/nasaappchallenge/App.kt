package me.tatocaster.nasaappchallenge

import android.content.Context
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : DaggerApplication() {
    lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    companion object {
        @JvmStatic
        fun getAppContext(context: Context): App = context.applicationContext as App
    }
}