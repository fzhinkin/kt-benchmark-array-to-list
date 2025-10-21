package fr.amanin.bench

import kotlin.random.Random
import kotlinx.benchmark.*

@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 1)
@State(Scope.Benchmark)
open class StringArrayTake {

    @Param("0", "1", "3", "17", "32", "1000", "100000")
    var arraySize: Int = 0

    @Param("first", "half", "all")
    var take: String = ""

    lateinit var array: Array<String>
    var n : Int = -1

    @Setup
    fun init() {
        array = Array(arraySize) { Random.nextInt(0, 200).toString() }
        n = when (take) {
            "first" -> 1
            "half" -> arraySize / 2
            else -> arraySize
        }
    }

    @Benchmark
    open fun takeBaseline(bh: Blackhole) {
        bh.consume(array.take(n))
    }

    @Benchmark
    open fun takeOptimized(bh: Blackhole) {
        bh.consume(array.takeNoLoop(n))
    }

    @Benchmark
    open fun takeLastBaseline(bh: Blackhole) {
        bh.consume(array.takeLast(n))
    }

    @Benchmark
    open fun takeLastOptimized(bh: Blackhole) {
        bh.consume(array.takeLastNoLoop(n))
    }

    @Benchmark
    open fun filterAfterTakeBaseline(bh: Blackhole) {
        bh.consume(array.take(n).filter { it[0] == '1' })
    }

    @Benchmark
    open fun filterAfterTakeOptimized(bh: Blackhole) {
        bh.consume(array.takeNoLoop(n).filter { it[0] == '1' })
    }

    @Benchmark
    open fun filterAfterTakeLastBaseline(bh: Blackhole) {
        bh.consume(array.takeLast(n).filter { it[0] == '1' })
    }

    @Benchmark
    open fun filterAfterTakeLastOptimized(bh: Blackhole) {
        bh.consume(array.takeLastNoLoop(n).filter { it[0] == '1' })
    }
}
