package ro.codespace.aoc.day15

import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.max
import kotlin.random.Random


fun main() {
    val code = day(15).readLine().split(",").map { it.toInt() }


    val inputQueue = LinkedBlockingQueue<Int>()
    val outputQueue = LinkedBlockingQueue<Int>()


    fun send(command: Int): Int {
        inputQueue.offer(command)
        return outputQueue.take()
    }

    val program = Program(code, { inputQueue.take() }, { outputQueue.offer(it.toInt()) })
    Thread { program() }.also { it.start() }


    val moves = mutableMapOf(Vector2(0, -1) to 1, Vector2(0, 1) to 2, Vector2(-1, 0) to 3, Vector2(1, 0) to 4)
    val knownPositions = mutableMapOf(Vector2(0, 0) to 1)


    fun explore(pos: Vector2) {

        val neighbours = moves.mapKeys { it.key + pos }

        neighbours.filter { it.key !in knownPositions }.forEach {
            val result = send(it.value)
            knownPositions[it.key] = result

            if (result != 0) {
                explore(it.key)
                send(
                    when (it.value) {
                        1 -> 2
                        2 -> 1
                        3 -> 4
                        4 -> 3
                        else -> throw Exception()
                    }
                )
            }

        }
    }

    explore(Vector2(0, 0))
    for (y in -21..21) {
        for (x in -21..21) {
            print(
                when (knownPositions[Vector2(x, y)]) {
                    0 -> "#"
                    1 -> "."
                    2 -> "@"
                    3 -> "?"
                    else -> " "
                }
            )
        }
        println()
    }


    val current = knownPositions.entries.first { it.value == 2 }.key
    val bfsQ = LinkedList<Pair<Vector2, Int>>()
    val visited = mutableSetOf(current)
    var maxTime = 0
    bfsQ.addFirst(current to 0)

    while (bfsQ.isNotEmpty()) {
        val (pos, time) = bfsQ.pollLast()
        visited.add(pos)
        maxTime = max(time, maxTime)
        moves.keys.map { it + pos }
            .filter { it in knownPositions }
            .filter { knownPositions[it] != 0 }
            .filter { it !in visited }
            .forEach {
                bfsQ.addFirst(it to time + 1)
            }
    }
    println(maxTime)
}