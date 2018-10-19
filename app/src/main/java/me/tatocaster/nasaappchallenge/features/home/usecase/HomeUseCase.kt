package me.tatocaster.nasaappchallenge.features.home.usecase

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import me.tatocaster.nasaappchallenge.common.UseCase
import me.tatocaster.nasaappchallenge.di.executor.PostExecutionThread
import me.tatocaster.nasaappchallenge.di.executor.ThreadExecutor
import javax.inject.Inject

class HomeUseCase
@Inject
constructor(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : UseCase(threadExecutor, postExecutionThread) {
    lateinit var code: String
    fun execute(code: String, useCaseObserver: ResourceSubscriber<*>) {
        this.code = code
        super.execute(useCaseObserver)
    }

    override fun buildUseCaseFlowable(): Flowable<*> {
        return Flowable.just(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        /*val useCaseObservable = loginRepository.intro()
        this.code = ""
        return useCaseObservable*/
    }
}