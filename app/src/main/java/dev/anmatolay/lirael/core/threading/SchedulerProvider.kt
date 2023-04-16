package dev.anmatolay.lirael.core.threading

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun mainThread(): Scheduler
}
