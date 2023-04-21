package dev.anmatolay.lirael

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.transform.RoundedCornersTransformation
import coil.transition.CrossfadeTransition
import coil.transition.Transition
import dev.anmatolay.lirael.core.di.KoinInitializer
import dev.anmatolay.lirael.domain.usecase.user.GetUserUseCase
import dev.anmatolay.lirael.domain.usecase.MonitoringUseCase
import org.koin.android.ext.android.inject

class LiraelApplication : Application(), ImageLoaderFactory {

    private val getUserUseCase by inject<GetUserUseCase>()
    private val monitoringUseCase by inject<MonitoringUseCase>()

    private var userId: String? = null

    override fun onCreate() {
        super.onCreate()

        KoinInitializer.init(this)

        monitoringUseCase.setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
        monitoringUseCase.setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)

        getUserUseCase.getCachedUserIdOrDefault()
            .doOnSuccess { userId = it }
            .subscribe()
            .dispose()

        monitoringUseCase.run {
            setUpAnalyticsAndLogging(userId)
            setUserProperties()
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .placeholder(R.drawable.ic_loading_image)
            .build()
    }
}
