package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
open class StringArrayToList {

    @Param("3", "32", "1000", "100000")
    var arraySize: Int? = null
    lateinit var array: Array<String>

    @Setup
    fun init() {
        val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
    }

    @Benchmark
    open fun usingStd() = array.toList()

    @Benchmark
    open fun usingStdHacked() = array.toListHacked()

    @Benchmark
    open fun usingListOf() = array.toListUsingListOf()


    @Benchmark
    open fun usingCopyOfAsList() = array.toListUsingAsList()

    @Benchmark
    open fun filterAfterStd() = array.toList().filter { it[0] == '1' }

    @Benchmark
    open fun filterAfterCopyOfAsList() = array.toList().filter { it[0] == '1' }

    @Benchmark
    open fun filterAfterListOf() = array.toList().filter { it[0] == '1' }
}
