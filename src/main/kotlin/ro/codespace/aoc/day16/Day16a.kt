package ro.codespace.aoc.day16

import ro.codespace.aoc.day
import kotlin.math.abs


fun multiplierFor(n: Int, order: Int): Int {
    return listOf(0, 1, 0, -1)[((n + 1) / order) % 4]
}

fun fft(input: List<Int>, phaseCount: Int): List<Int> {
    var result = input
    repeat(phaseCount) {
        result = calcPhase(result)
    }
    return result
}

fun calcPhase(input: List<Int>): List<Int> {
    return input.indices.map { order ->
        input.withIndex().sumBy {
            it.value * multiplierFor(it.index, order+1)
        }
    }.map { abs(it) % 10 }
}

fun main() {
    val input = day(16).readLine()!!.chunked(1).map { it.toInt() }
    println(input)
    println(fft(input, 100).take(8).joinToString(""))
}
