package fr.amanin.bench

fun <T> Array<T>.takeNoLoop(n: Int) : List<T> {
    require(n >= 0) { "Requested element count $n is less than zero." }
    if (n == 0) return emptyList()
    if (n >= size) return toList()
    if (n == 1) return listOf(this[0])
    return copyOfRange(0, n).asList()
}

fun <T> Array<T>.takeLastNoLoop(n: Int) : List<T> {
    require(n >= 0) { "Requested element count $n is less than zero." }
    if (n == 0) return emptyList()
    val size = size
    if (n >= size) return toList()
    if (n == 1) return listOf(this[size - 1])
    return copyOfRange(size - n, size).asList()
}
