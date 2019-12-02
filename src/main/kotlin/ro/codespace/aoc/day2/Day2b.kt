package ro.codespace.aoc.day2

fun runProgram(program: List<Int>, noun: Int, verb: Int): Int {
    val runnableProgram = program.toMutableList()
    runnableProgram[1] = noun
    runnableProgram[2] = verb
    var ip = 0
    while (true) {
        ip = solveOpcode(ip, runnableProgram)
        if (runnableProgram[ip] == 99) break
    }
    return runnableProgram[0]
}

fun main() {
    val program = readLine()!!.split(",").map { it.toInt() }

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