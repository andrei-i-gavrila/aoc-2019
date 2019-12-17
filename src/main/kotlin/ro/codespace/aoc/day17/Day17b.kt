package ro.codespace.aoc.day17

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day

fun main() {
    val code = day(17).readLine().split(",").map { it.toInt() }

    val map = readMap(code)

    val scaffold = map.mapIndexed { y, row ->
        row.mapIndexed { x, c -> Vector2(x, y) to c }
    }.flatten().filter { it.second != '.' }.toMap()

    val start = scaffold.filter { it.value != '#' }.keys.first()
    var current = start
    var direction = getDirection(scaffold[current]!!)

    val steps = mutableListOf<String>()

    while (true) {
        val nextPoint = current.neighbours.firstOrNull { it in scaffold && it != (current - direction) } ?: break
        val nextDirection = nextPoint - current
        val turnChar = getTurn(direction, nextDirection)
        steps.add(turnChar.toString())
        direction = nextDirection
        var counter = 0
        while ((current + direction) in scaffold) {
            current += direction
            counter++
        }
        steps.add(counter.toString())

//        while ((current + direction) in scaffold) {
//            current += direction
//            steps.add("M")
//        }
    }
    val message = steps.joinToString(",")
    println(message)

    println(map.joinToString("\n") { it.joinToString("") })
    findFunctions(steps)
}

fun IntRange.intersects(other: IntRange): Boolean {
    return first in other || last in other || (first < other.first && last > other.last)
}

fun findFunctions(steps: List<String>) {
    val n = steps.size - 1
    for (startA in 0..n) {
        for (endA in startA + 1..n) {
            val A = steps.subList(startA, endA + 1)
            val rangeA = startA..endA
            if (A.joinToString(",").length > 20) break

            for (startB in 0..n) {
                if (startB in rangeA) break
                for (endB in startB + 1..n) {
                    val rangeB = startB..endB
                    if (rangeA.intersects(rangeB)) break

                    val B = steps.subList(startB, endB + 1)
                    if (B.joinToString(",").length > 20) break

                    for (startC in 0..n) {
                        if (startC in rangeA || startC in rangeB) break
                        for (endC in startC + 1..n) {
                            val rangeC = startC..endC
                            if (rangeC.intersects(rangeA) || rangeC.intersects(rangeB)) break

                            val C = steps.subList(startC, endC + 1)
                            if (C.joinToString(",").length > 20) break

                            if (canFit(steps, A, B, C)) {
                                println(A)
                                println(B)
                                println(C)
                                return
                            }
                        }
                    }
                }
            }
        }
    }
}

fun canFit(all: List<String>, A: List<String>, B: List<String>, C: List<String>): Boolean {
    if (all.isEmpty()) return true
    var result = false
    if (canPrefix(A, all)) {
        result = result || canFit(all.subList(A.size, all.size), A, B, C)
    }
    if (canPrefix(B, all)) {
        result = result || canFit(all.subList(B.size, all.size), A, B, C)
    }
    if (canPrefix(C, all)) {
        result = result || canFit(all.subList(C.size, all.size), A, B, C)
    }
    return result
}


fun canPrefix(prefix: List<String>, all: List<String>): Boolean {
    if (prefix.size > all.size) return false
    for (i in prefix.indices) {
        if (prefix[i] != all[i]) return false
    }
    return true
}

fun getDirection(representation: Char): Vector2 {
    return when (representation) {
        '>' -> Vector2.RIGHT
        '<' -> Vector2.LEFT
        'v' -> Vector2.DOWN
        '^' -> Vector2.UP
        else -> throw Exception()
    }
}

fun getTurn(from: Vector2, to: Vector2) = if (to == Vector2(from.y, -from.x)) 'L' else 'R'