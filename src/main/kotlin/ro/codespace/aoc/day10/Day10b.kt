package ro.codespace.aoc.day10

import ro.codespace.aoc.Vector2
import kotlin.math.PI
import kotlin.math.atan2


fun angle(station: Vector2, asteroid: Vector2): Double {
    var atan2 = atan2((asteroid.y - station.y).toDouble(), (asteroid.x - station.x).toDouble())
    atan2 += PI/2
    if (atan2 < 0) return atan2 + 2 * PI
    return atan2
}


fun main() {
    val asteroids = readAsteroids("10")
    val station = asteroids.maxBy { getVisible(asteroids, it).size }!!
    val needed = getVisible(asteroids, station).sortedBy { angle(station, it) }[199]

    println(needed)
}