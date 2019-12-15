package ro.codespace.aoc.day15

import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
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
    val thread = Thread { program() }.also { it.start() }

    var current = Vector2(0, 0)

    val moves = mutableMapOf(Vector2(0, -1) to 1, Vector2(0, 1) to 2, Vector2(-1, 0) to 3, Vector2(1, 0) to 4)
    val knownPositions = mutableMapOf(current to 3)

    val random = Random(123)

    fun Vector2.moveTo(): Int {
        return send(moves[this - current]!!).also {
            knownPositions[this] = it
            if (it != 0) {
                current = this
            }
        }
    }

    while (true) {
        val neighbours = moves.keys.map { it + current }

        if (neighbours.all { it in knownPositions }) {
            neighbours.filter { knownPositions[it]!! == 1 }.random(random).moveTo()
        } else if (neighbours.any { it !in knownPositions }) {
            neighbours.filter { it !in knownPositions }.random(random).moveTo()
        }
        if (knownPositions[current] == 2) {
            break
        }
    }

    for (y in -20..20) {
        for (x in -20..20) {
            print(when(knownPositions[Vector2(x, y)]) {
                0 -> "#"
                1 -> "."
                2 -> "@"
                3 -> "?"
                else -> " "
            })
        }
        println()
    }

    println(current)
    current = Vector2(0, 0)
    val bfsQ = LinkedList<Pair<Vector2, Int>>()
    val visited = mutableSetOf(current)
    bfsQ.addFirst(current to 0)

    while (bfsQ.isNotEmpty()) {
        val (pos, time) = bfsQ.pollLast()
        visited.add(pos)
        if (knownPositions[pos] == 2) {
            println(time)
            break
        }
        moves.keys.map { it + pos }.filter { it in knownPositions && knownPositions[it] != 0 }.filter { it !in visited }.forEach {
            bfsQ.addFirst(it to time + 1)
        }
    }
}
