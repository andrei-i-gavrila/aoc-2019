package ro.codespace.aoc.day5

import ro.codespace.aoc.day


fun getValue(position: Int, program: List<Int>, mode: Int): Int {
    return if (mode == 0) program[program[position]] else program[position]
}


fun getModes(opcode: Int): List<Int> {
    return listOf(opcode % 100, opcode / 100 % 10, opcode / 1000 % 10, opcode / 10000 % 10)
}


fun solveOpcode(position: Int, program: MutableList<Int>, reader: () -> Int): Int {

    val (D, C, B, A) = getModes(program[position])

    return when (D) {
        1 -> {
            program[program[position + 3]] = getValue(position + 1, program, C) + getValue(position + 2, program, B)
            4
        }
        2 -> {
            program[program[position + 3]] = getValue(position + 1, program, C) * getValue(position + 2, program, B)
            4
        }

        3 -> {
            program[program[position + 1]] = reader()
            2
        }
        4 -> {
            println("Output: ${getValue(position + 1, program, C)}")
            2
        }
        5 -> {
            if (getValue(position + 1, program, C) != 0) {
                getValue(position + 2, program, B) - position
            } else {
                3
            }
        }
        6 -> {
            if (getValue(position + 1, program, C) == 0) {
                getValue(position + 2, program, B) - position
            } else {
                3
            }
        }
        7 -> {
            program[program[position + 3]] = if (getValue(position + 1, program, C) < getValue(position + 2, program, B)) {
                1
            } else {
                0
            }
            4
        }
        8 -> {
            program[program[position + 3]] = if (getValue(position + 1, program, C) == getValue(position + 2, program, B)) {
                1
            } else {
                0
            }
            4
        }
        else -> 0
    }
}


fun main() {
    val program = day(5).readLine().split(",").map { it.toInt() }.toMutableList()

    var pos = 0
    while (true) {
        val jump = solveOpcode(pos, program) { 1 }
        if (jump == 0) {
            return
        }
        pos += jump
    }
}