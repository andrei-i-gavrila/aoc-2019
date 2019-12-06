package ro.codespace.aoc.day6

import ro.codespace.aoc.day
import java.util.*


fun countOrbits(graph: Map<String, List<String>>): Int {
    var orbits = 0
    val queue = LinkedList<Pair<String, Int>>()
    queue.offer("COM" to 0)

    while (queue.isNotEmpty()) {
        val (node, indirections) = queue.poll()

        orbits += indirections
        graph[node]?.forEach {
            queue.offer(it to indirections + 1)
        }
    }
    return orbits
}


fun readGraph(): Map<String, List<String>> {
    return day(6).readLines().map {
        it.split(")")
    }.groupBy({ it[0] }, { it[1] })
}

fun main() {
    val graph = readGraph()

    val orbits = countOrbits(graph)

    println(orbits)

}
