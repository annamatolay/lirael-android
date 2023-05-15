package dev.anmatolay.lirael.domain.model

data class CookingHistory(
    val epochDateTime: Long,
    var numberOfCooking: Int = 0,
)
