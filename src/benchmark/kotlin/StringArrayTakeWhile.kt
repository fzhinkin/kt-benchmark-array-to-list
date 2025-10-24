package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
open class StringArrayTakeWhile {
    @Param("0", "1", "3", "17", "32", "1000", "100000")
    var arraySize: Int = 0

    @Param("first", "middle", "none")
    var stopwordPosition: String = ""

    lateinit var array: Array<String>

    val arrayTakeWhileBaseline: List<String> by lazy {
        array.takeWhile { it != "STOP" }
    }

    val arrayTakeWhileOptimized: List<String> by lazy {
        array.takeWhileNoLoop { it != "STOP" }
    }

    @Setup
    open fun init() {
        val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
        val stopwordIdx = when (stopwordPosition) {
            "first" -> 0
            "middle" -> arraySize / 2
            else -> -1
        }
        if (arraySize > 0 && stopwordIdx >= 0) {
            array[stopwordIdx] = "STOP"
        }
    }

    @Benchmark
    open fun takeWhileBaseline(bh: Blackhole) {
        bh.consume(array.takeWhile { it != "STOP" })
    }

    @Benchmark
    open fun takeWhileOptimized(bh: Blackhole) {
        bh.consume(array.takeWhileNoLoop { it != "STOP" })
    }

    @Benchmark
    open fun filterAfterTakeWhileBaseline(bh: Blackhole) {
        bh.consume(arrayTakeWhileBaseline.filter { it[0] == '1' })
    }

    @Benchmark
    open fun filterAfterTakeWhileOptimized(bh: Blackhole) {
        bh.consume(arrayTakeWhileOptimized.filter { it[0] == '1' })
    }
}
