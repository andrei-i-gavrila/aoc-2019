package ro.codespace.aoc.day9

import ro.codespace.aoc.Program
import ro.codespace.aoc.day


fun main() {
    val code = day(9).readLine().split(",").map { it.toInt() }

    val testCode = listOf(104, 1125899906842624, 99)
    val program = Program(code, { 1 })
    println(program())
}