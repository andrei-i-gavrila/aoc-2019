package ro.codespace.aoc.day1

import ro.codespace.aoc.day

fun main() {
    println(day(1).readLines().map { it.toInt() }.sumBy { it / 3 - 2 })
}