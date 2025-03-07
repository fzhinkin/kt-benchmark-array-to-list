package fr.amanin.bench

import kotlin.math.min

fun <T> Array<T>.takeNoLoop(n: Int) : List<T> {
    return when (n) {
        0 -> emptyList()
        1 -> if (isEmpty()) emptyList() else listOf(this[0])
        else -> copyOfRange(0, min(n, size)).asList()
    }
}

fun <T> Array<T>.takeLastNoLoop(n: Int) : List<T> {
    return when (n) {
        0 -> emptyList()
        1 -> if (isEmpty()) emptyList() else listOf(this[size-1])
        else -> copyOfRange(size - min(n, size), size).asList()
    }
}