package ro.codespace.aoc.day20

import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import java.util.*
import javax.xml.soap.Node

fun main() {
    val input = day("20b").readLines().map {
        it.toList()
    }

    val nodes = input.mapIndexed { y, row ->
        row.mapIndexed { x, e ->
            Vector2(x, y) to e
        }
    }.flatten().filter { it.second != '#' && it.second != ' ' }.toMap()

    val bounds = Vector2(input.map { it.size }.max()!!, input.size)


    val portals = findAllPortals(nodes, bounds)
    val portalsByPos = portals.associateBy { it.exit }

    val portalPairs = portals.associateBy { it.identifier to it.interior }.let { indexedPortals ->
        indexedPortals.map {
            it.value to indexedPortals[it.value.identifier to !it.value.interior]
        }
    }.toMap()

    val portalConnections = portals.map {
        it to findReachablePortals(it, nodes, portalsByPos)
    }.toMap()

    val start = portals.first { it.identifier == "AA" }
    val destination = portals.first { it.identifier == "ZZ" }

    val time = solveB(portalConnections, portalPairs, start, destination)
    println(time)

}

data class NodeB(val portal: Portal, val layer: Int)

fun solveB(
    portalConnections: Map<Portal, List<Pair<Portal, Int>>>,
    portalPairs: Map<Portal, Portal?>,
    start: Portal,
    destination: Portal
): Int {

    val q = PriorityQueue<Pair<NodeB, Int>>(compareBy { it.second })
    val visited = mutableSetOf<NodeB>()

    q.offer(NodeB(start, 0) to 0)
    val times = mutableMapOf(NodeB(start, 0) to 0)

    while (q.isNotEmpty()) {
        val (current, time) = q.poll()
        visited.add(current)
        if (current.portal == destination && current.layer == 0) {
            return time
        }

        val nextPortals = portalConnections[current.portal]
        nextPortals?.filter { it.first != start }?.forEach { (portal, ttd) ->
            val nextNode = if (portal == destination) {
                if (current.layer != 0) return@forEach
                NodeB(destination, current.layer) to time + ttd
            } else {
                if (current.layer == 0 && !portal.interior) return@forEach
                val nextLayer = if (portal.interior) current.layer + 1 else current.layer - 1
                NodeB(portalPairs[portal]!!, nextLayer) to time + ttd + 1
            }

            if (nextNode.second < times[nextNode.first] ?: Int.MAX_VALUE) {
                q.add(nextNode)
            }

        }
    }
    return 0
}

fun findReachablePortals(
    portal: Portal,
    nodes: Map<Vector2, Char>,
    portalsByPos: Map<Vector2, Portal>
): List<Pair<Portal, Int>> {
    val q = LinkedList<Pair<Vector2, Int>>()
    val visited = mutableSetOf<Vector2>()
    q.addFirst(portal.exit to 0)

    val portals = mutableListOf<Pair<Portal, Int>>()

    while (q.isNotEmpty()) {
        val (current, time) = q.pollLast()
        visited.add(current)
        if (current in portalsByPos && current != portal.exit) {
            portals.add(portalsByPos[current]!! to time)
        }

        current.neighbours.filter { it in nodes && nodes[it] == '.' && it !in visited }.forEach {
            q.addFirst(it to time + 1)
        }
    }
    return portals
}

fun findAllPortals(nodes: Map<Vector2, Char>, mapBounds: Vector2): List<Portal> {
    return nodes.filter { it.value.isUpperCase() }.mapNotNull {
        val pair = it.key.neighbours.firstOrNull { nodes[it]?.isUpperCase() ?: false }
        if (pair != null) {
            val identifier = listOf(nodes[pair]!!, it.value).sorted().joinToString("")
            val exit = (withAdjacentPoint(nodes, it.key) ?: withAdjacentPoint(nodes, pair))!!.second
            val interior = listOf(it.key, pair).flatMap { it.neighbours }.none { !it.inBounds(mapBounds) }
            Portal(exit, identifier, interior)
        } else {
            null
        }
    }.toSet().toList()
}

data class Portal(val exit: Vector2, val identifier: String, val interior: Boolean)