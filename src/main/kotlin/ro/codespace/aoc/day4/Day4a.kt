package ro.codespace.aoc.day4


fun canBePassword(pass: String): Boolean {
    return pass.zipWithNext().any { it.first == it.second }
            && pass.zipWithNext().all { it.first <= it.second }
}


fun main() {
    val input = "231832-767346"

    val (left, right) = input.split("-").map { it.toInt() }

    println((left..right).count { canBePassword(it.toString()) })
}