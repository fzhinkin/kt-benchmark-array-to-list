package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
open class StringArrayToList {

    @Param("3", "32", "1000", "1000000")
    var arraySize: Int? = null
    // For now, do not make it varying, because array to list should only copy references
    val txtSize: Int = 32
    lateinit var array: Array<String>

    @Setup
    fun init() {
        val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
        array = Array(arraySize) { Random.nextBytes(txtSize).joinToString() }
    }

    @Benchmark
    open fun usingStd() = array.toList()

    @Benchmark
    open fun usingListOf() = array.toListUsingListOf()


    @Benchmark
    open fun usingCopyOfAsList() = array.toListUsingAsList()
}
