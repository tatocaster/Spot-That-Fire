package me.tatocaster.nasaappchallenge

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import me.tatocaster.nasaappchallenge.di.executor.JobExecutor
import me.tatocaster.nasaappchallenge.di.executor.PostExecutionThread
import me.tatocaster.nasaappchallenge.di.executor.ThreadExecutor
import me.tatocaster.nasaappchallenge.di.executor.UIThread


@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @Binds
    abstract fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread

}