package ro.codespace.aoc.day18

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*

fun main() {
    val input = day("18b").readLines()

    val nodes = input.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            Vector2(x, y) to c
        }.filter { it.second != '#' }
    }.flatten().toMap()

    val starts = nodes.entries.filter { it.value.isDigit() }.sortedBy { it.value }.map { it.key }
    println(starts)
    val positions = nodes.entries.filter { it.value.isLetterOrDigit() }.map { it.value to it.key }.toMap()
    val dependencies = starts.map { getDependencies(nodes, it) }

    println(solveB(nodes, positions, dependencies, positions.keys.count { it.isLowerCase() }))
}

data class NodeB(val keyChain: Set<Char>, val lastKeys: List<Char>)

fun solveB(
    nodes: Map<Vector2, Char>,
    positions: Map<Char, Vector2>,
    dependencies: List<Map<Char, Set<Char>>>,
    keyCount: Int
): Int {

    val startNode = NodeB(setOf(), listOf('0', '1', '2', '3'))
    val times = mutableMapOf(startNode to 0)
    val openQueue = PriorityQueue<Pair<NodeB, Int>>(compareBy { it.second })

    openQueue.add(startNode to 0)

    while (openQueue.isNotEmpty()) {
        val (node, time) = openQueue.poll()

        if (node.keyChain.size == keyCount) {
            return time
        }

        dependencies.forEachIndexed { bot, deps ->
            nextNodes(node.keyChain, deps).forEach {
                val distanceToNode = times[node]!! + bestTime(nodes, positions[node.lastKeys[bot]]!!, positions[it]!!)
                val newLastKeys = node.lastKeys.toMutableList()
                newLastKeys[bot] = it

                val nextNode = NodeB(node.keyChain + it, newLastKeys.toList())

                if (distanceToNode < times.getOrDefault(nextNode, Int.MAX_VALUE)) {
                    times[nextNode] = distanceToNode
                    openQueue.add(nextNode to distanceToNode)
                }
            }
        }
    }
    return 0
}