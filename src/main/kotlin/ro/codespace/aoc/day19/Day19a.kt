package ro.codespace.aoc.day19

import ro.codespace.aoc.InputReaderProvider
import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day

fun main() {
    val code = day(19).readLine().split(",").map { it.toInt() }

    var position = Vector2(0, 0)

    val provider = InputReaderProvider()

    for (y in 0 until 50) {
        for (x in 0 until 50) {
            provider.send(x)
            provider.send(y)
        }
    }

    val map = mutableMapOf<Vector2, Int>()

    val outputFunction: (String) -> Unit  = {
        map[position] = it.toInt()
        if (position.x < 50) position = Vector2(position.x + 1, position.y)
        else position = Vector2(0, position.y + 1)
    }


    repeat(50 * 50) {
        Program(code, provider, outputFunction)()
    }

    println(map.count { it.value == 1 })


    for (y in 0 until 50) {
        for (x in 0 until 50) {
           print(if (map[Vector2(x, y)] == 1) "#" else ".")
        }
        println()
    }

}

