package dev.anmatolay.lirael.core.presentation

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.jakewharton.rxrelay3.PublishRelay
import timber.log.Timber

abstract class BaseActivity<Event: UiEvent> : AppCompatActivity() {

    protected abstract val viewModel: BaseUdfViewModel<out UiState, out UiEvent>
    private val eventRelay = PublishRelay.create<Event>()

    protected fun triggerEvent(event: Event) = eventRelay.accept(event)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setEventObserver(eventRelay)
        viewModel.onViewCreated()
        Timber.d("${this.javaClass.simpleName} - onCreate")
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
        Timber.d("${this.javaClass.simpleName} - onResume")
    }

    @CallSuper
    override fun onPause() {
        viewModel.onViewPaused()
        super.onPause()
        Timber.d("${this.javaClass.simpleName} - onPause")
    }

    @CallSuper
    override fun onDestroy() {
        viewModel.onDestroyView()
        super.onDestroy()
        Timber.d("${this.javaClass.simpleName} - onDestroy")
    }

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(this@BaseActivity, observer)
    }
}
