package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
open class StringArrayTake {

    @Param("32", "1000", "100000")
    var arraySize: Int? = null
    @Param("half", "all")
    var take: String? = null
    // For now, do not make it varying, because array to list should only copy references
    val txtSize: Int = 32
    lateinit var array: Array<String>
    var n : Int? = null

    @Setup
    fun init() {
        val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
        n = if (take == "half") arraySize / 2 else arraySize
    }

    @Benchmark
    open fun takeUsingStd() = array.take(n!!)

    @Benchmark
    open fun takeUsingNoLoop() = array.takeNoLoop(n!!)

    @Benchmark
    open fun takeLastUsingStd() = array.takeLast(n!!)

    @Benchmark
    open fun takeLastUsingNoLoop() = array.takeLastNoLoop(n!!)
}
