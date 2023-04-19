package dev.anmatolay.lirael.core.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceFragmentCompat
import com.jakewharton.rxrelay3.PublishRelay
import timber.log.Timber

abstract class BasePreferenceFragment<Event: UiEvent> : PreferenceFragmentCompat() {

    protected abstract val viewModel: BaseUdfViewModel<out UiState, out UiEvent>
    private val eventRelay = PublishRelay.create<Event>()

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, observer)
    }

    protected fun triggerEvent(event: Event) = eventRelay.accept(event)

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setEventObserver(eventRelay)
        viewModel.onViewCreated()
        Timber.d("${this.javaClass.simpleName} - onViewCreated")
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
    override fun onDestroyView() {
        viewModel.onDestroyView()
        super.onDestroyView()
        Timber.d("${this.javaClass.simpleName} - onDestroyView")
    }
}
