package ro.codespace.aoc.day21

import ro.codespace.aoc.Program
import ro.codespace.aoc.day

fun main() {
    val code = day(21).readLine().split(",").map { it.toInt() }


    fun outputFunction(it: String) {
        print(it.toInt().toChar())
    }

    val instructions = """
        OR A J
        AND B J
        AND C J
        NOT J J
        AND D J
        WALK
        
    """.trimIndent().asIterable().iterator()

    fun inputFunction(): Int {
        return (if (instructions.hasNext()) instructions.next().toInt() else 0).also {
            print(it.toChar())
        }
    }

    val program = Program(code, ::inputFunction, outputFunction = ::outputFunction)
    program()
    println(program.lastOutput)
}
