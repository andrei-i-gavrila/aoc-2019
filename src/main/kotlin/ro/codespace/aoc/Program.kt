package ro.codespace.aoc

import java.math.BigInteger
import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque

class Program<T : Number>(
    code: List<T>,
    private val reader: () -> Int = { 0 },
    private val outputFunction: (String) -> Unit = { println("Output: $it") }
) {
    private val runCode = Code(code)
    var lastOutput = ""
    private var relativeBase = BigInteger.ZERO

    fun solve(): BigInteger {
        var pos = 0
        while (true) {
            val opCode = OpCode(pos)
            if (opCode.isStop()) break
            pos = opCode()
        }
        return runCode[0]
    }

    operator fun invoke() = solve().toInt()


    private inner class OpCode(val position: Int) {
        val value get() = runCode[position]
        val code get() = value % BigInteger.valueOf(100)

        fun argument(offset: Int) = Argument(position + offset, value.toInt() / 10.pow(offset + 1) % 10)


        val first get() = argument(1)
        val second get() = argument(2)
        val third get() = argument(3)

        fun jump(offset: Int) = jumpTo(position + offset)
        fun jumpTo(offset: Int) = offset

        operator fun invoke(): Int {
            return when (code.toInt()) {
                1 -> {
                    third.writeTo(first.value + second.value)
                    jump(4)
                }
                2 -> { // multiply
                    third.writeTo(first.value * second.value)
                    jump(4)
                }
                3 -> { // read
                    first.writeTo(reader().toBigInteger())
                    jump(2)
                }
                4 -> { // print
                    lastOutput = first.value.toString()
                    outputFunction(lastOutput)
                    jump(2)
                }
                5 -> { // jump if true
                    if (first.value != 0.toBigInteger()) jumpTo(second.value.toInt())
                    else jump(3)
                }
                6 -> { // jump if false
                    if (first.value == 0.toBigInteger()) jumpTo(second.value.toInt())
                    else jump(3)
                }
                7 -> { // less than
                    third.writeTo(if (first.value < second.value) BigInteger.ONE else BigInteger.ZERO)
                    jump(4)
                }
                8 -> { // equals
                    third.writeTo(if (first.value == second.value) BigInteger.ONE else BigInteger.ZERO)
                    jump(4)
                }
                9 -> { // adjust relative base
                    relativeBase += first.value
                    jump(2)
                }
                else -> jump(0)
            }
        }

        fun isStop(): Boolean {
            return value == BigInteger.valueOf(99)
        }
    }

    private inner class Argument(val position: Int, val mode: Int) {
        val value
            get() = when (mode) {
                0 -> runCode[runCode[position].toInt()]
                1 -> runCode[position]
                2 -> runCode[(runCode[position] + relativeBase).toInt()]
                else -> throw Exception()
            }

        fun writeTo(value: BigInteger) {
            when (mode) {
                0 -> runCode[runCode[position].toInt()] = value
                2 -> runCode[(runCode[position] + relativeBase).toInt()] = value
            }
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

class Code<T : Number>(runCode: List<T>) {
    private val code = runCode.withIndex().associate { it.index to it.value.toLong().toBigInteger() }.toMutableMap()

    operator fun get(position: Int): BigInteger {
        return code.getOrDefault(position, BigInteger.ZERO)
    }

    operator fun set(position: Int, value: BigInteger) {
        if (position < 0) throw Exception()
        code[position] = value
    }
}