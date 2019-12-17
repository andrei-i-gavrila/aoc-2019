package ro.codespace.aoc.day17

import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day

fun main() {
    val code = day(17).readLine().split(",").map { it.toInt() }

    val map = readMap(code)
    val intersectionPoints = intersectionPoints(map)

    println(intersectionPoints.sumBy { it.x * it.y })
    printMap(map, intersectionPoints)
}

fun printMap(map: List<List<Char>>, intersectionPoints: Set<Vector2>) {
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (Vector2(x, y) in intersectionPoints) print("O")
            else print(map[y][x])
        }
        println()
    }
}

fun readMap(code: List<Int>): List<List<Char>> {
    val map = mutableListOf(mutableListOf<Char>())

    Program(code, outputFunction = {
        val char = it.toInt().toChar()
        if (char == '\n') map.add(mutableListOf())
        else map.last().add(char)
    })()
    map.removeIf { it.isEmpty() }

    return map
}

fun intersectionPoints(map: List<List<Char>>): Set<Vector2> {
    val intersectionPoints = map.mapIndexed { y, row -> row.mapIndexed { x, el -> Vector2(x, y) to el } }
        .flatten().filter { it.second != '.' }.filter {
            it.first.neighbours.filter {
                it.inBounds(Vector2(map[0].size, map.size))
            }.count { map[it.y][it.x] != '.' } == 4
        }.mapTo(hashSetOf()) { it.first }
    return intersectionPoints
}