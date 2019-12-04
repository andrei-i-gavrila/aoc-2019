package ro.codespace.aoc.day4

import ro.codespace.aoc.day


fun canBePasswordB(pass: String): Boolean {
    return pass.zipWithNext().all { it.first <= it.second } && (1..9).any {
        pass.contains((it * 11).toString()) && !pass.contains((it * 111).toString())
    }
}


fun main() {
    val (left, right) = day(4).readLine().split("-").map { it.toInt() }

    println((left..right).count { canBePasswordB(it.toString()) })

    println(canBePasswordB("112233"))
    println(canBePasswordB("123444"))
    println(canBePasswordB("111122"))
}