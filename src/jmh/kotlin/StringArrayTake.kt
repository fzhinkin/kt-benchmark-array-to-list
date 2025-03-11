package fr.amanin.bench

import org.openjdk.jmh.annotations.*
import java.util.HexFormat
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Warmup(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@Fork(value = 2)
open class StringArrayTake {

    @State(Scope.Thread)
    open class ArrayInput {

        @Param("32", "1000", "1000000")
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
            val hex = HexFormat.of()
            array = Array(arraySize) { hex.formatHex(Random.nextBytes(txtSize)) }
            n = if (take == "half") arraySize / 2 else arraySize
        }
    }

    @Benchmark
    open fun takeUsingStd(state: ArrayInput) = state.array.take(state.n!!)

    @Benchmark
    open fun takeUsingNoLoop(state: ArrayInput) = state.array.takeNoLoop(state.n!!)

    @Benchmark
    open fun takeLastUsingStd(state: ArrayInput) = state.array.takeLast(state.n!!)

    @Benchmark
    open fun takeLastUsingNoLoop(state: ArrayInput) = state.array.takeLastNoLoop(state.n!!)
}
