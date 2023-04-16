package dev.anmatolay.lirael.core.threading.impl

import dev.anmatolay.lirael.core.threading.SchedulerProvider
import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()
    override fun computation() = Schedulers.trampoline()
    override fun mainThread() = Schedulers.trampoline()
}
