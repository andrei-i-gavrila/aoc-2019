package ro.codespace.aoc.day4

import ro.codespace.aoc.day


fun canBePasswordb(pass: String): Boolean {
    return pass.zipWithNext().all { it.first <= it.second } && (1..9).any {
        pass.contains((it * 11).toString()) && !pass.contains((it * 111).toString())
    }
}


fun main() {
    val (left, right) = day(4).readLine().split("-").map { it.toInt() }

    println((left..right).count { canBePasswordb(it.toString()) })

    println(canBePasswordb("112233"))
    println(canBePasswordb("123444"))
    println(canBePasswordb("111122"))
}