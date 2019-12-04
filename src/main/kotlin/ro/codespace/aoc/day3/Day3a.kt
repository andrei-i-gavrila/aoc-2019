package ro.codespace.aoc.day3

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day


fun makeMoves(moves: List<Pair<String, Int>>): List<Vector2> {
    var current = Vector2(0, 0)
    val spaces = mutableListOf<Vector2>()
    for (move in moves) {
        when (move.first) {
            "R" -> {
                spaces.addAll(move(current, Vector2(0, 1), move.second))
                current += Vector2(0, move.second)
            }
            "L" -> {
                spaces.addAll(move(current, Vector2(0, -1), move.second))
                current -= Vector2(0, move.second)
            }
            "U" -> {
                spaces.addAll(move(current, Vector2(-1, 0), move.second))
                current -= Vector2(move.second, 0)
            }
            "D" -> {
                spaces.addAll(move(current, Vector2(1, 0), move.second))
                current += Vector2(move.second, 0)
            }
        }
    }
    return spaces
}

fun move(current: Vector2, dir: Vector2, distance: Int): MutableList<Vector2> {
    val spaces = mutableListOf<Vector2>()

    for (it in 1..distance) {
        spaces.add(current + dir * it)
    }
    return spaces
}

fun transformMoves(line: String): List<Pair<String, Int>> {
    return line.split(",").map {
        it.first().toString() to it.substring(1).toInt()
    }
}


fun main() {

    val (moves1, moves2) = day(3).readLines().map { transformMoves(it) }.map { makeMoves(it) }


    println(moves1.intersect(moves2).map {
        it.manhattanScale
    }.min())
}