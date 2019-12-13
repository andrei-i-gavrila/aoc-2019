package ro.codespace.aoc.day13

import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day

fun main() {
    val code = day(13).readLine().split(",").map { it.toInt() }


    val screen = mutableMapOf<Vector2, Int>()

    var outputCounter = 0
    var drawX = 0
    var drawY = 0
    var mode = 0
    var score = 0
    val outputF: (String) -> Unit = {
        when (outputCounter % 3) {
            0 -> drawX = it.toInt()
            1 -> drawY = it.toInt()
            2 -> {
                if (drawX == -1 && drawY == 0) {
                    score = it.toInt()
                } else {
                    mode = it.toInt()
                    screen[Vector2(drawX, drawY)] = mode
                }
            }
        }
        outputCounter++
    }

    val program = Program(code, outputFunction = outputF)
    program()

    println(screen.count { it.value == 2 })
}