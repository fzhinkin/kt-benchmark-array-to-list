package fr.amanin.bench

import org.openjdk.jmh.annotations.*
import java.util.HexFormat
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Warmup(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@Fork(value = 2)
open class StringArrayToList {

    @State(Scope.Thread)
    open class ArrayInput {

        @Param(/*"0", "1",*/ "3", "32", "1000", "1000000")
        var arraySize: Int? = null
        // For now, do not make it varying, because array to list should only copy references
        val txtSize: Int = 32
        lateinit var array: Array<String>

        @Setup
        fun init() {
            val arraySize = checkNotNull(arraySize) { "arraySize parameter not set"}
            val hex = HexFormat.of()
            array = Array(arraySize) { hex.formatHex(Random.nextBytes(txtSize)) }
        }
    }

    @Benchmark
    open fun usingStd(state: ArrayInput) = state.array.toList()

    @Benchmark
    open fun usingListOf(state: ArrayInput) = state.array.toListUsingListOf()


    @Benchmark
    open fun usingCopyOfAsList(state: ArrayInput) = state.array.toListUsingAsList()
}
