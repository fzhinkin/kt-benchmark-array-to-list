package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
open class StringArrayToList {

    @Param("0", "1", "3", "17", "32", "1000", "100000")
    var arraySize: Int = 0
    lateinit var array: Array<String>

    @Setup
    fun init() {
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
    }

    @Benchmark
    open fun toListBaseline(bh: Blackhole) {
        bh.consume(array.toList())
    }

    @Benchmark
    open fun toListOptimized(bh: Blackhole) {
        bh.consume(array.toListUsingAsList())
    }

    @Benchmark
    open fun filterAfterToListBaseline(bh: Blackhole) {
        bh.consume(array.toList().filter { it[0] == '1' })
    }

    @Benchmark
    open fun filterAfterToListOptimized(bh: Blackhole) {
        bh.consume(array.toList().filter { it[0] == '1' })
    }
}
