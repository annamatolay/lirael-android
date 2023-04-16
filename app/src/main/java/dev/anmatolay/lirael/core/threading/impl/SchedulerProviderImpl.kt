package dev.anmatolay.lirael.core.threading.impl

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProviderImpl : SchedulerProvider {

    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()
}
