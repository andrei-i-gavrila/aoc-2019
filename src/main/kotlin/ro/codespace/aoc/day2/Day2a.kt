package ro.codespace.aoc.day2

import ro.codespace.aoc.Program
import ro.codespace.aoc.day


fun main() {
    val code = day(2).readLine()!!.split(",").map { it.toInt() }.toMutableList()
    code[1] = 12
    code[2] = 2

    val program = Program(code)
    println(program())

}