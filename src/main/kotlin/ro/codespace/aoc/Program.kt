package ro.codespace.aoc

import com.sun.jmx.remote.internal.ArrayQueue
import java.util.*
import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque
import java.util.function.Supplier

class Program(
    code: List<Int>,
    private val reader: () -> Int = { 0 },
    private val outputFunction: (Int) -> Unit = { println("Output: $it") }
) {
    private val runCode = code.toMutableList()
    var lastOutput = 0

    fun solve(): Int {
        var pos = 0
        while (true) {
            val opCode = OpCode(pos)
            if (opCode.isStop()) break
            pos = opCode()
        }
        return runCode[0]
    }

    operator fun invoke() = solve()


    private inner class OpCode(val position: Int) {
        val value get() = runCode[position]
        val code get() = value % 100

        fun argument(offset: Int) = Argument(position + offset, value / 10.pow(offset + 1) % 10)


        val first get() = argument(1)
        val second get() = argument(2)
        val third get() = argument(3)

        fun jump(offset: Int) = jumpTo(position + offset)
        fun jumpTo(offset: Int) = offset

        operator fun invoke(): Int {
            return when (code) {
                1 -> {
                    third.writeTo(first.value + second.value)
                    jump(4)
                }
                2 -> { // multiply
                    third.writeTo(first.value * second.value)
                    jump(4)
                }
                3 -> { // read
                    first.writeTo(reader())
                    jump(2)
                }
                4 -> { // print
                    lastOutput = first.value
                    outputFunction(first.value)
                    jump(2)
                }
                5 -> { // jump if true
                    if (first.value != 0) jumpTo(second.value)
                    else jump(3)
                }
                6 -> { // jump if false
                    if (first.value == 0) jumpTo(second.value)
                    else jump(3)
                }
                7 -> { // less than
                    third.writeTo(if (first.value < second.value) 1 else 0)
                    jump(4)
                }
                8 -> { // equals
                    third.writeTo(if (first.value == second.value) 1 else 0)
                    jump(4)
                }
                else -> jump(0)
            }
        }

        fun isStop(): Boolean {
            return value == 99
        }
    }

    private inner class Argument(val position: Int, val mode: Int) {
        val value get() = if (mode == 0) runCode[runCode[position]] else runCode[position]
        fun writeTo(value: Int) {
            runCode[runCode[position]] = value
        }
    }
}

class InputReaderProvider : () -> Int {
    private val inputQueue: BlockingDeque<Int> = LinkedBlockingDeque()

    fun send(number: Int) {
        inputQueue.offer(number)
    }

    override operator fun invoke(): Int {
        return inputQueue.take()
    }
}