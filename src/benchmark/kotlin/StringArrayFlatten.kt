package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 5)
@State(Scope.Benchmark)
open class StringArrayFlatten {

    @Param("1", "3", "100")
    var arraySize: Int = 0

    @Param("1", "32", "1000")
    var innerSize: Int = 0

    lateinit var array: Array<Array<String>>

    @Setup
    open fun init() {
        array = Array(arraySize) {
            Array(innerSize) { Random.nextInt(0, 200).toString() }
        }
    }

    @Benchmark
    open fun flattenBaseline(bh: Blackhole) {
        bh.consume(array.flatten())
    }

    @Benchmark
    open fun flattenOptimized(bh: Blackhole) {
        bh.consume(array.flattenOptimized())
    }

    @Benchmark
    open fun filterAfterFlattenBaseline(bh: Blackhole) {
        bh.consume(array.flatten().filter { it[0] == '1' })
    }

    @Benchmark
    open fun filterAfterFlattenOptimized(bh: Blackhole) {
        bh.consume(array.flattenOptimized().filter { it[0] == '1' })
    }
}
