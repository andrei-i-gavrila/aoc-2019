package ro.codespace.aoc.day7

import com.google.common.collect.Collections2
import ro.codespace.aoc.InputReaderProvider
import ro.codespace.aoc.Program
import ro.codespace.aoc.day
import ro.codespace.aoc.read


class ProgramRunner(val program: Program<Int>) : Runnable {

    override fun run() {
        program()
        println("program done")
    }
}

fun computeSignalInLoop(configuration: List<Int>, code: List<Int>): Int {
    println("Running phase order: $configuration")

    val readers = mutableListOf<InputReaderProvider>()
    val outputFunctions = mutableListOf<(String) -> Unit>()
    for (phase in configuration) {
        val reader = InputReaderProvider()
        reader.send(phase)
        readers.add(reader)
    }

    for (i in configuration.indices) {
        if (i + 1 < readers.size) {
            outputFunctions.add {
                readers[i + 1].send(it.toInt())
            }
        } else {
            outputFunctions.add {
                readers[0].send(it.toInt())
            }
        }
    }
    readers[0].send(0)
    val runners = configuration.indices.map {
        ProgramRunner(Program(code, readers[it], outputFunctions[it]))
    }

    runners.map {
        Thread(it).also { it.start() }
    }.forEach {
        it.join()
    }


    return runners.last().program.lastOutput.toInt()
}

fun main() {
    val code = day(7).readLine().split(",").map { it.toInt() }
    val permutations = Collections2.permutations(listOf(5, 6, 7, 8, 9))

    permutations.map {
        computeSignalInLoop(it, code)
    }.max().let {
        println(it)
    }

}