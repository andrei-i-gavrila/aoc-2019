package ro.codespace.aoc.day19

import com.google.common.io.Resources
import ro.codespace.aoc.InputReaderProvider
import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.io.File
import java.util.*

fun main() {
    val code = day(19).readLine().split(",").map { it.toInt() }


    val provider = InputReaderProvider()
    val posQueue = LinkedList<Vector2>()
    val interrogatedPositions = mutableSetOf<Vector2>()
    fun sendPos(pos: Vector2) {
        provider.send(pos.x)
        provider.send(pos.y)
        interrogatedPositions.add(pos)
        posQueue.addFirst(pos)
    }
    sendPos(Vector2(0, 0))

    val map = mutableMapOf<Vector2, Int>()
    var done = false
    val outputFunction: (String) -> Unit = {
        val position = posQueue.pollLast()

        map[position] = it.toInt()

        val offsetX = Vector2(-99, 0)
        val offsetY = Vector2(0, -99)
        if (map[position] == 1 && map[position + offsetX] == 1 && map[position+offsetY] == 1 && map[position + offsetX + offsetY] == 1) {
            println(position + offsetX + offsetY)
            done = true
        }


        if (map[position] == 1 && !done) {
            for (y in 0..6) {
                for (x in 0..6) {
                    val pos = Vector2(position.x + x, position.y + y)
                    if (pos !in interrogatedPositions) {
                        sendPos(pos)
                    }
                }
            }
        }

    }


    while (!done) {
        Program(code, provider, outputFunction)()
    }

    println(map.count { it.value == 1 })

    val file = Resources.getResource("day19out.txt").file.let { File(it) }.printWriter()
    for (y in 0..map.map { it.key.y }.max()!!) {
        for (x in 0..map.map { it.key.x }.max()!!) {
            file.print(
                when {
                    map[Vector2(x, y)] == 1 -> "#"
                    map[Vector2(x, y)] == 0 -> "."
                    else -> " "
                }
            )
        }
        file.println()
    }
    file.close()
}

