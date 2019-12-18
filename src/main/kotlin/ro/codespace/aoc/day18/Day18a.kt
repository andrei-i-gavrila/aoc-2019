package ro.codespace.aoc.day18

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*

fun main() {
    val input = day("18test").readLines()

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

fun solve(
    nodes: Map<Vector2, Char>,
    positions: Map<Char, Vector2>,
    dependencies: Map<Char, Set<Char>>,
    keyCount: Int
): Pair<Int, List<Char>> {

    val startNode = ""
    val times = mutableMapOf(startNode to 0)
    val openQueue = PriorityQueue<Pair<String, Int>>(compareBy { it.second })
    openQueue.add(startNode to 0)

    val prev = mutableMapOf<String, Char>()



    while (openQueue.isNotEmpty()) {
        val (current, time) = openQueue.poll()

        if (current.length == keyCount) {
            val path = mutableListOf<Char>()
            var currentPath = current
            while (currentPath in prev) {
                path.add(prev[currentPath]!!)
                currentPath = (currentPath.toSet() - prev[currentPath]!!).sorted().joinToString("")
            }
            return time to path.reversed()
        }

        nextNodes(current, dependencies).forEach {
            val nextNode = (current.toList() + it).sorted().joinToString("")
            val prevPosition = if (current in prev) {
                positions[prev[current]!!]!!
            } else {
                positions['@']!!
            }

            val distanceToNode = times[current]!! + bestTime(nodes, prevPosition, positions[it]!!)


            if (distanceToNode < times.getOrDefault(nextNode, Int.MAX_VALUE)) {
                times[nextNode] = distanceToNode
                prev[nextNode] = it
                openQueue.add(nextNode to distanceToNode)
            }
        }
    }
    return 0 to listOf()
}

fun nextNodes(current: String, dependencies: Map<Char, Set<Char>>): List<Char> {
    val currentLetters = current.toSet()
    return dependencies.entries.filter {
        currentLetters.containsAll(it.value) && it.key !in currentLetters
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

fun toposort(dependencies: Map<Char, Set<Char>>): List<List<Char>> {
    val zeroNodes = mutableListOf<List<Char>>()
    var degrees = dependencies.mapValues { it.value.toMutableSet() }
    while (degrees.isNotEmpty()) {
        println(degrees)
        degrees.filterValues { it.isEmpty() }.keys.let {
            zeroNodes.add(it.toList())
        }
        degrees = degrees.filterValues { it.isNotEmpty() }.mapValues {
            it.value.minus(zeroNodes.last()).toMutableSet()
        }
    }

    return zeroNodes
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
    return dependencies;
}