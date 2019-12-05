package ro.codespace.aoc.day2

import ro.codespace.aoc.Program
import ro.codespace.aoc.day

fun runProgram(program: List<Int>, noun: Int, verb: Int): Int {
    val runnableProgram = program.toMutableList()
    runnableProgram[1] = noun
    runnableProgram[2] = verb
    return Program(runnableProgram)()
}

fun main() {
    val program = day(2).readLine()!!.split(",").map { it.toInt() }

    val max = program.size
    for (noun in 0 until max) {
        for (verb in 0 until max) {
            val result = runProgram(program, noun, verb)
            if (result == 19690720) {
                println(100 * noun + verb)
                return
            }
        }
    }
}