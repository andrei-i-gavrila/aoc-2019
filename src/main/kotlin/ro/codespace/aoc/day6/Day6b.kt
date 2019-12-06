package ro.codespace.aoc.day6

import ro.codespace.aoc.day

fun parentGraph(graph: Map<String, List<String>>): Map<String, String> {
    return graph.entries.flatMap { entry ->
        entry.value.map { it to entry.key }
    }.toMap()
}

fun findLcaJumps(parentGraph: Map<String, String>, you: String, san: String): Int {
    var parent = parentGraph.getValue(you)
    val visited = mutableMapOf(parent to 0)
    while (parentGraph[parent] in parentGraph) {
        visited[parentGraph.getValue(parent)] = visited.getValue(parent) + 1
        parent = parentGraph.getValue(parent)
    }

    parent = parentGraph.getValue(san)
    var jumps = 0
    while (parent !in visited) {
        parent = parentGraph.getValue(parent)
        jumps++
    }

    return visited[parent]!! + jumps
}

fun main() {
    val graph = readGraph()
    val parent = parentGraph(graph)
    println(findLcaJumps(parent, "YOU", "SAN"))
}