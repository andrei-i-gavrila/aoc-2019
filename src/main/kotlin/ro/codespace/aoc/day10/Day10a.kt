package ro.codespace.aoc.day10

import com.google.common.math.IntMath
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import kotlin.math.abs


fun gcd(a: Int, b: Int): Int {
    return IntMath.gcd(abs(a), abs(b))
}

fun getVisible(asteroids: Set<Vector2>, thisOne: Vector2): List<Vector2> {
    return asteroids.minus(thisOne).filter {
        val diff = it - thisOne
        val gcd = gcd(diff.x, diff.y)
        val unit = diff / gcd

        var check = thisOne + unit
        while (check != it) {
            if (check in asteroids) {
                return@filter false
            }
            check += unit
        }

        true
    }
}


fun main() {
    val asteroids = readAsteroids("10")
    val maxVis = asteroids.map { it to getVisible(asteroids, it).size }.maxBy { it.second }
    println(maxVis)
}

fun readAsteroids(file: String): Set<Vector2> {
    val map = day(file).readLines().map { it.toList() }

    val asteroids = map.asSequence().mapIndexed { rowI, row ->
        row.mapIndexed { colI, e ->
            Pair(Vector2(colI, rowI), e)
        }
    }.flatten().filter { it.second != '.' }.map { it.first }.toSet()
    return asteroids
}