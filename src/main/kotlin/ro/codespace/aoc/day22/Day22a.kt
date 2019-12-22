package ro.codespace.aoc.day22

import ro.codespace.aoc.day

fun main() {
    val  moves = day("22").readLines()

    println(getPosition(moves, 2019, 10007))
}

fun getPosition(moves: List<String>, startPosition: Long, cardCount: Long): Long {
    var position = startPosition
    moves.forEach { move ->
        when {
            move.startsWith("cut") -> {
                position -= getNumber(move)
            }
            move.startsWith("deal into new stack") -> {
                position = cardCount - 1 - position
            }
            move.startsWith("deal with increment") -> {
                position = (position * getNumber(move)) % cardCount
            }
        }
        if (position < 0) {
            position += cardCount
        }

    }
    return position
}

fun getNumber(move: String): Int {
    return Regex(".*?(-?\\d+)").matchEntire(move)!!.groupValues[1].toInt()
}
