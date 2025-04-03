package fr.amanin.bench

fun <T> Array<T>.toListUsingListOf() : List<T> {
    return if (isEmpty()) emptyList() else listOf(*this)
}

fun <T> Array<T>.toListUsingAsList() : List<T> {
    return when (size) {
        0 -> emptyList()
        1 -> listOf(this[0])
        else -> copyOf().asList()
    }
}

fun <T> Array<T>.toListHacked() : List<T> {
    return ArrayAsListHacked(this).toMutableList()
}


private class ArrayAsListHacked<T>(val array: Array<T>) : List<T> by array.asList() {
    fun toArray(): Array<out Any?> {
        return array
    }
}