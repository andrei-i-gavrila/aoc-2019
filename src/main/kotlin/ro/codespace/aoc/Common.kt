package ro.codespace.aoc

import com.google.common.io.Resources
import kotlin.math.abs

fun readInt() = readLine()?.toIntOrNull()

data class Vector2(val x: Int, val y: Int) {
    operator fun plus(vector2: Vector2) = Vector2(x + vector2.x, y + vector2.y)
    operator fun minus(vector2: Vector2) = Vector2(x - vector2.x, y - vector2.y)
    operator fun times(scale: Int) = Vector2(x * scale, y * scale)
    val manhattanScale get() = abs(x) + abs(y)
}

fun read(file: String) = Resources.getResource(file).openStream().bufferedReader()

fun day(number: Int) = read("day$number.txt")