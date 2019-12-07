package ro.codespace.aoc.day7

import com.google.common.collect.Collections2
import ro.codespace.aoc.InputReaderProvider
import ro.codespace.aoc.Program
import ro.codespace.aoc.day

fun computeSignal(configuration: List<Int>, code: List<Int>): Int {
    println("Running phase order: $configuration")
    val reader = InputReaderProvider()
    var signal = 0

    for (phase in configuration) {
        val program = Program(code, reader)
        reader.send(phase)
        reader.send(signal)
        program()

        signal = program.lastOutput
    }
    return signal
}

fun main() {
    val code = day(7).readLine().split(",").map { it.toInt() }
    val permutations = Collections2.permutations(listOf(0, 1, 2, 3, 4))

    permutations.map {
        computeSignal(it, code)
    }.max().let {
        println(it)
    }

}