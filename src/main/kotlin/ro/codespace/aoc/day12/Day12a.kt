package ro.codespace.aoc.day12

import ro.codespace.aoc.day
import kotlin.math.abs

data class Moon(var x: Int, var y: Int, var z: Int) {
    var vx = 0
    var vy = 0
    var vz = 0


    val kineticEnergy get() = abs(vx) + abs(vy) + abs(vz)
    val potentialEnergy get() = abs(x) + abs(y) + abs(z)

    fun calculateVelocity(other: Moon) {
//        println("$x ${other.x} ${compareValues(x, other.x)}")
        vx += compareValues(other.x, x)
        vy += compareValues(other.y, y)
        vz += compareValues(other.z, z)
    }

    fun updatePositions() {
        x += vx
        y += vy
        z += vz
    }
}

fun readMoon(line: String): Moon {
    val (x, y, z) = line.substring(1 until line.length - 1).split(",").map { it.substringAfter("=") }.map { it.toInt() }
    return Moon(x, y, z)
}

fun main() {
    val moons = day("12").readLines().map { readMoon(it) }.onEach { println(it) }


    repeat(1000) { tick ->
        runTick(moons)
    }

    println(moons.sumBy { it.kineticEnergy * it.potentialEnergy })
}

fun runTick(moons: List<Moon>) {
    moons.forEach { a ->
        moons.forEach { b ->
            a.calculateVelocity(b)
        }
    }
    moons.forEach { it.updatePositions() }
}