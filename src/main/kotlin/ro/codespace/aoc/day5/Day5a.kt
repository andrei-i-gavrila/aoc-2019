package ro.codespace.aoc.day5

import ro.codespace.aoc.Program
import ro.codespace.aoc.day


fun main() {
    val code = day(5).readLine().split(",").map { it.toInt() }.toMutableList()

    Program(code) { 1 }()
}