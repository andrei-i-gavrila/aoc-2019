package ro.codespace.aoc.day9

import ro.codespace.aoc.Program
import ro.codespace.aoc.day

fun main() {
    val code = day(9).readLine().split(",").map { it.toInt() }

    val program = Program(code, { 2 })
    println(program())
}