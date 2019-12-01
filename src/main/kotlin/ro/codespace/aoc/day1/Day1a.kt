package ro.codespace.aoc.day1

import ro.codespace.aoc.readInt

fun main() {
    println(generateSequence { readInt() }.sumBy { it / 3 - 2 })
}