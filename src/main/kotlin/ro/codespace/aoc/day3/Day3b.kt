package ro.codespace.aoc.day3

import ro.codespace.aoc.day


fun main() {

    val (moves1, moves2) = day(3).readLines().map { transformMoves(it) }.map { makeMoves(it) }

    val intersectionPoints = moves1.intersect(moves2)

    val count1 =
        moves1.mapIndexed { index, vector2 -> vector2 to (index + 1) }.filter { it.first in intersectionPoints }
            .reversed().toMap()
    val count2 =
        moves2.mapIndexed { index, vector2 -> vector2 to (index + 1) }.filter { it.first in intersectionPoints }
            .reversed().toMap()

    println(intersectionPoints.map {
        count1[it]!! + count2[it]!!
    }.min())

}