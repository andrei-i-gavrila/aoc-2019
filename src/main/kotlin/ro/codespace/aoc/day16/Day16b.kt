package ro.codespace.aoc.day16

import ro.codespace.aoc.day
import kotlin.math.abs
import kotlin.math.min


fun computePartials(input: List<Int>): List<Int> {
    val partials = mutableListOf(0)
    input.forEach { partials.add(it + partials.last()) }
    return partials
}

fun getMeaningSequences(n: Int, order: Int): List<IntRange> {
    var i = order - 1
    val ranges = mutableListOf<IntRange>()
    while (i < n) {
        ranges.add(i until min(i + order, n))
        i += 2 * order
    }
    return ranges
}

fun sumUpSequences(partials: List<Int>, sequences: List<IntRange>): Int {
    var parity = -1
    return sequences.sumBy {
        parity *= -1
        parity * (partials[it.last + 1] - partials[it.first])
    }
}

fun fastFFTPhase(n: Int, partials: List<Int>, sequences: List<List<IntRange>>): List<Int> {
    val result = mutableListOf(0)
    (0 until n)
        .map { sumUpSequences(partials, sequences[it]) }
        .forEach { result.add(result.last() + abs(it) % 10) }
    return result
}

fun fastFFT(input: List<Int>, phaseCount: Int): List<Int> {
    var result = computePartials(input)
    val meaningSequences = input.indices.map { getMeaningSequences(input.size, it + 1) }
    repeat(phaseCount) {
        println(it)
        result = fastFFTPhase(input.size, result, meaningSequences)
    }
    return input.indices.map { result[it + 1] - result[it] }
}

fun main() {
    val input = day(16).readLine()!!.chunked(1).map { it.toInt() }
    val actualInput = (1..10000).flatMap { input }
    val offset = actualInput.take(7).joinToString("").toInt()
    val result = fastFFT(actualInput, 100)


    val message = result.drop(offset).take(8)
    println(message.joinToString(""))
}