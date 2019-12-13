package ro.codespace.aoc.day12

import com.google.common.math.LongMath
import ro.codespace.aoc.day

fun <T> runUntilSame(moons: List<Moon>, extractor: (Moon) -> T): Long {
    val set = mutableSetOf<List<T>>()

    var ticks = 0L
    while (true) {
        val state = moons.map(extractor)
        if (state in set) {
            break
        }
        set.add(state)

        runTick(moons)
        ticks++
    }

    return ticks
}


fun main() {
    val moons = day("12").readLines().map { readMoon(it) }

    val tx = runUntilSame(moons.map { it.copy() }) { it.x to it.vx }
    val ty = runUntilSame(moons.map { it.copy() }) { it.y to it.vy }
    val t = (tx * ty / LongMath.gcd(tx, ty))
    val tz = runUntilSame(moons.map { it.copy() }) { it.z to it.vz }

    println(t * tz / LongMath.gcd(t, tz))
}