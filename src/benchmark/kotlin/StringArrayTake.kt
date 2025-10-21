package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
open class StringArrayTake {

    @Param("0", "1", "3", "17", "32", "1000", "100000")
    var arraySize: Int? = null
    @Param("first", "half", "all")
    var take: String? = null
    lateinit var array: Array<String>
    var n : Int = -1

    @Setup
    fun init() {
        val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
        n = when (take) {
            "first" -> 1
            "half" -> arraySize / 2
            else -> arraySize
        }
    }

    @Benchmark
    open fun takeUsingStd() = array.take(n)

    @Benchmark
    open fun takeUsingNoLoop() = array.takeNoLoop(n)

    @Benchmark
    open fun takeLastUsingStd() = array.takeLast(n)

    @Benchmark
    open fun takeLastUsingNoLoop() = array.takeLastNoLoop(n)

    @Benchmark
    open fun filterAfterStd() = array.take(n).filter { it[0] == '1' }

    @Benchmark
    open fun filterAfterNoLoop() = array.takeNoLoop(n).filter { it[0] == '1' }
}
