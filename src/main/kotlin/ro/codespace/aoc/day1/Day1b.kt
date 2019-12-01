package ro.codespace.aoc.day1

import ro.codespace.aoc.readInt

fun calcFuel(mass: Int): Int {
    if (mass / 3 - 2 <= 0) return 0
    return mass / 3 - 2 + calcFuel(mass / 3 - 2)
}

fun main() {
    println(generateSequence { readInt() }.sumBy { calcFuel(it) })
}