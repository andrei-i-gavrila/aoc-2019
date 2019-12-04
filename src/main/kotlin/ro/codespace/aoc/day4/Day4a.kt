package ro.codespace.aoc.day4

import ro.codespace.aoc.day


fun canBePassword(pass: String): Boolean {
    return pass.zipWithNext().any { it.first == it.second }
            && pass.zipWithNext().all { it.first <= it.second }
}


fun main() {

    val (left, right) = day(4).readLine().split("-").map { it.toInt() }

    println((left..right).count { canBePassword(it.toString()) })
}