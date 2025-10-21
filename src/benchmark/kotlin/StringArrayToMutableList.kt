package fr.amanin.bench

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Measurement
import kotlinx.benchmark.Param
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import kotlinx.benchmark.Warmup
import kotlin.random.Random

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
//@State(Scope.Benchmark)
open class StringArrayToMutableList {

    @Param("1000")
    var arraySize: Int? = null
    lateinit var array: Array<String>

    @Setup
    fun init() {
        val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
    }

    @Benchmark
    open fun usingStd() = array.toMutableList()

    @Benchmark
    open fun usingHacked() = array.toListHacked()
}
