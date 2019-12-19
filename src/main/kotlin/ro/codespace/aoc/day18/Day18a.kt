package ro.codespace.aoc.day18

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*

fun main() {
    val input = day("18").readLines()

    val nodes = input.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            Vector2(x, y) to c
        }.filter { it.second != '#' }
    }.flatten().toMap()

    val start = nodes.entries.first { it.value == '@' }.key
    val positions = nodes.entries.filter { it.value.isLetter() || it.value == '@' }.map { it.value to it.key }.toMap()

    val dependencies = getDependencies(nodes, start)
    println(solve(nodes, positions, dependencies, positions.keys.count { it.isLowerCase() }))
}

data class Node(val keyChain: Set<Char>, val lastKey: Char)

fun solve(
    nodes: Map<Vector2, Char>,
    positions: Map<Char, Vector2>,
    dependencies: Map<Char, Set<Char>>,
    keyCount: Int
): Int {

    val startNode = Node(setOf(), '@')
    val times = mutableMapOf(startNode to 0)
    val openQueue = PriorityQueue<Pair<Node, Int>>(compareBy { it.second })
    openQueue.add(startNode to 0)

    while (openQueue.isNotEmpty()) {
        val (node, time) = openQueue.poll()

        if (node.keyChain.size == keyCount) {
            return time
        }

        nextNodes(node.keyChain, dependencies).forEach {

            val distanceToNode = times[node]!! + bestTime(nodes, positions[node.lastKey]!!, positions[it]!!)

            val nextNode = Node(node.keyChain + it, it)

            if (distanceToNode < times.getOrDefault(nextNode, Int.MAX_VALUE)) {
                times[nextNode] = distanceToNode
                openQueue.add(nextNode to distanceToNode)
            }
        }
    }
    return 0
}

fun nextNodes(current: Set<Char>, dependencies: Map<Char, Set<Char>>): List<Char> {
    return dependencies.entries.filter {
        current.containsAll(it.value) && it.key !in current
    }.map { it.key }
}


val bestTimeCache = mutableMapOf<Pair<Vector2, Vector2>, Int>()
fun bestTime(nodes: Map<Vector2, Char>, from: Vector2, to: Vector2): Int {
    if (from to to in bestTimeCache) return bestTimeCache[from to to]!!
    val q = LinkedList<Pair<Vector2, Int>>()
    q.addFirst(from to 0)
    val visited = mutableSetOf<Vector2>()
    while (q.isNotEmpty()) {
        val (node, time) = q.pollLast()
        visited.add(node)
        if (node == to) {
            bestTimeCache[from to to] = time
            return time
        }
        node.neighbours.filter { it !in visited && it in nodes }.forEach {
            q.addFirst(it to time + 1)
        }
    }
    return -1
}


fun getDependencies(nodes: Map<Vector2, Char>, start: Vector2): Map<Char, Set<Char>> {
    val dependencies = mutableMapOf<Char, Set<Char>>()


    fun dfs(current: Vector2, visited: MutableSet<Vector2>, gates: MutableSet<Char>) {
        visited.add(current)

        val currentChar = nodes[current]!!
        if (currentChar.isLowerCase()) {
            dependencies.merge(currentChar, gates.toSet()) { old, new -> old + new }
        }
        if (currentChar.isUpperCase()) {
            gates.add(currentChar.toLowerCase())
        }

        current.neighbours.filter { it !in visited && it in nodes }.forEach {
            dfs(it, visited, gates)
        }

        if (currentChar.isUpperCase()) {
            gates.remove(currentChar.toLowerCase())
        }
        visited.remove(current)
    }
    dfs(start, mutableSetOf(), mutableSetOf())
    return dependencies
}