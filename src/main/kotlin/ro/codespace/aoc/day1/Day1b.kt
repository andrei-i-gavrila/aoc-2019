package ro.codespace.aoc.day1

import ro.codespace.aoc.day

fun calcFuel(mass: Int): Int {
    if (mass / 3 - 2 <= 0) return 0
    return mass / 3 - 2 + calcFuel(mass / 3 - 2)
}

fun main() {
    println(day(1).readLines().map { it.toInt() }.sumBy { calcFuel(it) })
}