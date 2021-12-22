package day15

import utils.readInput
import utils.toInts
import java.util.*

data class Point(val x: Int, val y: Int)
data class Node(
    val pos: Point,
    val travelCost: Int,
    var tentativeDistance: Int,
    var visited: Boolean = false,
) : Comparable<Node> {
    override fun compareTo(other: Node): Int {
        return this.tentativeDistance - other.tentativeDistance
    }
}

fun main() {

    fun getNeighbors(nodes: List<List<Node>>, point: Point): List<Node> {
        val neighbors = mutableListOf<Node>()
//        if (point.y > 0) {
//            neighbors.add(nodes[point.y-1][point.x])
//        }
        if (point.y < nodes.size - 1) {
            neighbors.add(nodes[point.y + 1][point.x])
        }
        if (point.x < nodes[0].size - 1) {
            neighbors.add(nodes[point.y][point.x + 1])
        }
//        if (point.x < 0) {
//            neighbors.add(nodes[point.y][point.x-1])
//        }
        return neighbors
    }

    fun part1(lines: List<String>): Int {
        val grid: List<List<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts()
        }

        val nodes: List<List<Node>> = grid.indices.map { y ->
            grid[y].indices.map { x ->
                Node(
                    Point(x, y),
                    if (x == 0 && y == 0) 0 else grid[y][x],
                    if (x == 0 && y == 0) 0 else 9999,
                )
            }
        }

        val maxY = grid.size - 1
        val maxX = grid[0].size - 1

        var searchNode: Node = nodes[0][0]

        while (!nodes[maxY][maxX].visited) {
            getNeighbors(nodes, searchNode.pos)
                .filter { !it.visited }
                .forEach {
                    it.tentativeDistance = minOf(it.tentativeDistance, it.travelCost + searchNode.tentativeDistance)
                }

            searchNode.visited = true

            val remainingNodes = nodes.flatten()
                .filter { !it.visited }
                .sortedBy { it.tentativeDistance }
            if (remainingNodes.isNotEmpty()) {
                searchNode = remainingNodes.first()
            }
        }

        return nodes[maxY][maxX].tentativeDistance
    }


    fun part2(lines: List<String>): Int {
        val grid: List<List<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts()
        }

        val sectionYSize = grid.size
        val sectionXSize = grid[0].size

        val fullgrid = (0 until sectionYSize * 5).map { y ->
            (0 until sectionXSize * 5).map { x ->
                val sectionX = x.div(sectionXSize)
                val sectionY = y.div(sectionYSize)
                val sectionXVal = x.mod(sectionXSize)
                val sectionYVal = y.mod(sectionYSize)

                val result = (grid[sectionYVal][sectionXVal] + sectionX + sectionY)
                if (result > 9) result - 9 else result
            }

        }

        val maxY = fullgrid.size - 1
        val maxX = fullgrid[0].size - 1

        val nodes: List<List<Node>> = fullgrid.indices.map { y ->
            fullgrid[y].indices.map { x ->
                Node(
                    Point(x, y),
                    if (x == 0 && y == 0) 0 else fullgrid[y][x],
                    if (x == 0 && y == 0) 0 else 9999,
                )
            }
        }

        val unvisited = PriorityQueue(nodes.flatten())

        var searchNode: Node = nodes[0][0]

        while (!nodes[maxY][maxX].visited) {
            if (searchNode.pos.x > 0) {
                nodes[searchNode.pos.y][searchNode.pos.x - 1].let {
                    if (!it.visited) {
                        unvisited.remove(it)
                        it.tentativeDistance = minOf(it.tentativeDistance, it.travelCost + searchNode.tentativeDistance)
                        unvisited.add(it)
                    }
                }
            }
            if (searchNode.pos.y > 0) {
                nodes[searchNode.pos.y - 1][searchNode.pos.x].let {
                    if (!it.visited) {
                        unvisited.remove(it)
                        it.tentativeDistance = minOf(it.tentativeDistance, it.travelCost + searchNode.tentativeDistance)
                        unvisited.add(it)
                    }
                }
            }
            if (searchNode.pos.x < maxX) {
                nodes[searchNode.pos.y][searchNode.pos.x + 1].let {
                    if (!it.visited) {
                        unvisited.remove(it)
                        it.tentativeDistance = minOf(it.tentativeDistance, it.travelCost + searchNode.tentativeDistance)
                        unvisited.add(it)
                    }
                }
            }
            if (searchNode.pos.y < maxY) {
                nodes[searchNode.pos.y + 1][searchNode.pos.x].let {
                    if (!it.visited) {
                        unvisited.remove(it)
                        it.tentativeDistance = minOf(it.tentativeDistance, it.travelCost + searchNode.tentativeDistance)
                        unvisited.add(it)
                    }
                }
            }
            searchNode.visited = true

            if (unvisited.size > 0) {
                searchNode = unvisited.poll()
            }
        }

        return nodes[maxY][maxX].tentativeDistance
    }

    val testInput = readInput("day15/test")

    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("day15/input")
    println(part1(input))
    println(part2(input))
}
