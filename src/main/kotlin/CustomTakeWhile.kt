package fr.amanin.bench

fun <T> Array<T>.takeWhileNoLoop(predicate: (T) -> Boolean) : List<T> {
    var i = 0
    while (i < size && predicate(this[i])) i++
    return if (i == 0) emptyList()
    else if (i == 1) listOf(this[0])
    else copyOfRange(0, i).asList()
}
