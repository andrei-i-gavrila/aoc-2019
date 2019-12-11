package ro.codespace.aoc.day11

import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day


fun main() {
    val code = day(11).readLine().split(",").map { it.toLong() }


    val painted = mutableMapOf<Vector2, Int>()

    var currentPos = Vector2(0, 0)
    var currentDir = Vector2(0, -1)

    val reader = {

        println()
        for (y in -5..5) {
            for (x in -5..5) {
                if (y != 0 || x != 0) {
                    val color = painted.getOrDefault(Vector2(currentPos.x + x, currentPos.y + y), 0)
                    print(if (color == 1) "#" else ".")
                } else {
                    print(when (currentDir) {
                        Vector2(0, -1) -> "^"
                        Vector2(0, 1) -> "v"
                        Vector2(-1, 0) -> "<"
                        Vector2(1, 0) -> ">"
                        else -> ""
                    })
                }
            }
            println()
        }
        println()



        painted.getOrDefault(currentPos, 0)
    }

    var outputCounter = 0

    val output: (String) -> Unit = {
        if (outputCounter % 2 == 0) {
            painted[currentPos] = it.toInt()
        } else {
            when (it.toInt()) {
                0 -> currentDir = Vector2(currentDir.y, -currentDir.x)
                1 -> currentDir = Vector2(-currentDir.y, currentDir.x)
            }
            currentPos += currentDir
        }
        outputCounter++
    }
    Program(code, reader, output)()

    println(painted.size)
}

