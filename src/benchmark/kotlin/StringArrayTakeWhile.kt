package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
open class StringArrayTakeWhile {
    @Param("0", "1", "3", "17", "32", "1000", "100000")
    var arraySize: Int = 0

    @Param("first", "middle", "none")
    var stopwordPosition: String = ""

    lateinit var array: Array<String>

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
    open fun takeWhileStd(bh: Blackhole) {
        bh.consume(array.takeWhile { it != "STOP" })
    }

    @Benchmark
    open fun takeWhileNoLoop(bh: Blackhole) {
        bh.consume(array.takeWhileNoLoop { it != "STOP" })
    }
}
