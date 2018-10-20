package me.tatocaster.nasaappchallenge.features.create.usecase

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.tatocaster.nasaappchallenge.common.UseCase
import me.tatocaster.nasaappchallenge.di.executor.PostExecutionThread
import me.tatocaster.nasaappchallenge.di.executor.ThreadExecutor
import javax.inject.Inject


class CreateUseCase
@Inject
constructor(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : UseCase(threadExecutor, postExecutionThread) {
    override fun buildUseCaseFlowable(): Flowable<*> {
        return Flowable.just(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        /*val useCaseObservable = loginRepository.intro()
        this.code = ""
        return useCaseObservable*/
    }
}