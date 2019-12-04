package ro.codespace.aoc.day4


fun canBePasswordb(pass: String): Boolean {
    return pass.zipWithNext().all { it.first <= it.second } && (1..9).any {
        pass.contains((it * 11).toString()) && !pass.contains((it * 111).toString())
    }
}


fun main() {
    val input = "231832-767346"

    val (left, right) = input.split("-").map { it.toInt() }

    println((left..right).count { canBePasswordb(it.toString()) })

    println(canBePasswordb("112233"))
    println(canBePasswordb("123444"))
    println(canBePasswordb("111122"))
}