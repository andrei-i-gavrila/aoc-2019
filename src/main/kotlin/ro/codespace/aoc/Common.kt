package ro.codespace.aoc

import com.google.common.io.Resources
import com.google.common.math.IntMath
import kotlin.math.abs

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(vector2: Vector2) = Vector2(x + vector2.x, y + vector2.y)
    operator fun minus(vector2: Vector2) = Vector2(x - vector2.x, y - vector2.y)
    operator fun times(scale: Int) = Vector2(x * scale, y * scale)
    operator fun div(scale: Int) = Vector2(x / scale, y / scale)

    val manhattanScale get() = abs(x) + abs(y)
}

fun read(file: String) = Resources.getResource(file).openStream().bufferedReader()

fun day(number: Int) = read("day$number.txt")
fun day(number: String) = read("day$number.txt")


fun Int.pow(n: Int): Int {
    return IntMath.pow(this, n)
}

