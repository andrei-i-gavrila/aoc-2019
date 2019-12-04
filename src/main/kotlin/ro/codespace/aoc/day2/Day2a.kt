package ro.codespace.aoc.day2

import ro.codespace.aoc.day

fun solveOpcode(position: Int, program: MutableList<Int>): Int {
    when (program[position]) {
        1 -> program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
        2 -> program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
    }

    return if (program[position] != 99) position + 4 else position

}

fun main() {
    val program = day(2).readLine()!!.split(",").map { it.toInt() }.toMutableList()
    program[1] = 12
    program[2] = 2
    var ip = 0
    while (true) {
        ip = solveOpcode(ip, program)
        if (program[ip] == 99) break
    }
    println(program[0])
}