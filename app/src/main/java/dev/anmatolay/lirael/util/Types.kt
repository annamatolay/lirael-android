package dev.anmatolay.lirael.util

/**
 * Map representation of [@see RecipeHistory]
 *
 * String: formatted DateTime (LLLL yyyy) [@see LocalDateProvider#getDefaultDateTimeFormatter]
 * Long: Epoch DateTime within the month of year related to the String key
 * Int: Times of cooking on the given day (related to the Long key)
 */
typealias CookingHistoryMap = Map<String, Map<Long, Int>>
