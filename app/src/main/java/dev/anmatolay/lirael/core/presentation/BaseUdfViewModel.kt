package dev.anmatolay.lirael.core.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

abstract class BaseUdfViewModel<State : UiState, Event : UiEvent> : ViewModel() {

    val uiState: LiveData<State>
        get() = _uiState
    private val _uiState = MutableLiveData<State>()

    private lateinit var eventRelay: Observable<out UiEvent>

    private var aliveDisposables = CompositeDisposable()
    private var foregroundDisposables = CompositeDisposable()

    /*
    * Handled by base view layer by default.
    * See [BaseActivity], [BaseFragment], [BaseDialogFragment]
    */
    fun setEventObserver(relay: PublishRelay<out UiEvent>) {
        eventRelay = relay.toFlowable(BackpressureStrategy.LATEST).toObservable()
    }

    @CheckReturnValue
    protected fun doOnUiEventReceived(action: (UiEvent) -> Unit) = eventRelay.doOnNext { action(it) }

    protected fun triggerUiStateChange(state: State) {
        _uiState.postValue(state)
    }

    protected fun Disposable.disposeOnPause() = foregroundDisposables.add(this)

    protected fun Disposable.disposeOnDestroy() = aliveDisposables.add(this)

    @CallSuper
    open fun onViewCreated() {
        aliveDisposables = CompositeDisposable()
        Timber.d("${this.javaClass.simpleName} - onViewCreated")
    }

    @CallSuper
    open fun onViewResumed() {
        foregroundDisposables = CompositeDisposable()
        Timber.d("${this.javaClass.simpleName} - onViewResumed")
    }

    @CallSuper
    open fun onViewPaused() {
        foregroundDisposables.clear()
        Timber.d("${this.javaClass.simpleName} - onViewPaused")
    }

    @CallSuper
    open fun onDestroyView() {
        aliveDisposables.clear()
        Timber.d("${this.javaClass.simpleName} - onDestroyView")
    }
}
