package fr.amanin.bench

fun <T> Array<Array<T>>.flattenOptimized() : List<T> {
    if (isEmpty()) return emptyList()

    val totalSizeLong = sumOf { it.size.toLong() }
    if (totalSizeLong == 0L) return emptyList()
    require(totalSizeLong <= Int.MAX_VALUE.toLong()) {
        "Sum of all arrays overflow maximum array capacity (of Int.MAX_VALUE)"
    }

    val outputArray = arrayOfNulls<Any?>(totalSizeLong.toInt())
    var offset = 0
    for (innerArray in this) {
        innerArray.copyInto(outputArray, offset)
        offset += innerArray.size
    }

    @Suppress("UNCHECKED_CAST")
    return outputArray.asList() as List<T>
}
