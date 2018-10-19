package me.tatocaster.nasaappchallenge.common;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import me.tatocaster.nasaappchallenge.di.executor.PostExecutionThread;
import me.tatocaster.nasaappchallenge.di.executor.ThreadExecutor;

public abstract class UseCase {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    protected UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    protected abstract Flowable buildUseCaseFlowable();

    /**
     * Executes the current use case.
     *
     * @param observer {@link ResourceSubscriber} which will be listening to the observable build
     *                 by {@link #buildUseCaseFlowable()} ()} method.
     */
    @SuppressWarnings("unchecked")
    public void execute(ResourceSubscriber observer) {
        final Flowable<?> flowable = this.buildUseCaseFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(flowable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Clear and dispose from current {@link CompositeDisposable}.
     */
    public void clear() {
        disposables.clear();
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        if (disposable != null) disposables.add(disposable);
    }
}
