package ro.codespace.aoc.day20

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*

fun main() {
    val input = day("20").readLines().map {
        it.toList()
    }

    val nodes = input.mapIndexed { y, row ->
        row.mapIndexed { x, e ->
            Vector2(x, y) to e
        }
    }.flatten().filter { it.second != '#' && it.second != ' ' }.toMap()


    val start = nodes.filter { it.value == '@' }.keys.first()


    println(solve(start, nodes))
}

private fun solve(start: Vector2, nodes: Map<Vector2, Char>): Int {
    val q = LinkedList<Pair<Vector2, Int>>()
    q.addFirst(start to 0)

    val visited = mutableSetOf(start)

    while (q.isNotEmpty()) {
        val (current, time) = q.pollLast()
        println(current)

        if (nodes[current] == '$') {
            return time
        }

        neighboursOf(nodes, current).filter { it !in visited }.forEach {
            if (nodes[it]!!.isUpperCase()) {
                val (portal, portalExit) = portal(nodes, it)
                q.addFirst(portalExit to time + 1)
                visited.add(portal)
                visited.add(portalExit)
            } else {
                q.addFirst(it to time + 1)
                visited.add(it)
            }
        }

    }
    return 0
}


fun withAdjacentPoint(nodes: Map<Vector2, Char>, current: Vector2): Pair<Vector2, Vector2>? {
    val adjPoints = current.neighbours.filter { nodes[it] == '.' }
    return if (adjPoints.isEmpty()) null
    else current to adjPoints.first()
}

val portalCache = mutableMapOf<Vector2, Pair<Vector2, Vector2>>()
fun portal(nodes: Map<Vector2, Char>, current: Vector2): Pair<Vector2, Vector2> {
    if (current in portalCache) return portalCache[current]!!

    val portalPair = current.neighbours.first { it in nodes && nodes[it]!!.isUpperCase() }

    val exit = nodes
        .filter { it.value == nodes[current] && current != it.key && it.key.neighbours.any { nodes[it] == nodes[portalPair] } }
        .map {
            withAdjacentPoint(nodes,it.key) ?: withAdjacentPoint(nodes, it.key.neighbours.first { nodes[it] == nodes[portalPair] })
        }.first()!!

    portalCache[current] = exit
    return exit
}

fun neighboursOf(nodes: Map<Vector2, Char>, current: Vector2): List<Vector2> {
    if (nodes[current] == '@') return current.neighbours.filter { it in nodes && nodes[it] != 'A' }
    if (nodes[current] == '.') return current.neighbours.filter { it in nodes }
    return listOf()
}

