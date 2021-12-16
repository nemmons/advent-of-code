package day09

import utils.readInput
import utils.toInts

typealias Point = Pair<Int, Int>
typealias Basin = Set<Point>
typealias Explored = Set<Point>

fun main() {

    fun isLowpoint(x: Int, y: Int, lines: List<List<Int>>): Boolean {
        val leftCheck = (x == 0 || lines[y][x] < lines[y][x-1])
        if (!leftCheck) return false

        val topCheck = (y == 0 || lines[y][x] < lines[y-1][x])
        if (!topCheck) return false

        val maxX = lines[0].count() - 1
        val rightCheck = (x == maxX || lines[y][x] < lines[y][x+1])
        if (!rightCheck) return false

        val maxY = lines.count() - 1
        val bottomCheck = (y == maxY || lines[y][x] < lines[y+1][x])
        if (!bottomCheck) return false

        return true

    }

    fun part1(lines: List<String>): Int {
        val grid: List<List<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts()
        }

        return grid.mapIndexed { y, line ->
            val lowPoints = line.filterIndexed { x, _  -> isLowpoint(x, y, grid) }
            val risklevels = lowPoints.map { it + 1 }
            risklevels.sum()
        }.sum()
    }


    fun getBasin(x: Int, y: Int, lines: List<List<Int>>, explored: Set<Point> = setOf()): Pair<Basin, Explored> {
        val point = Pair(x,y)

        if (lines[y][x] == 9) return Pair(setOf(), setOf(point))
        if (explored.contains(point)) return Pair(setOf(), setOf(point))

        val neighbors: MutableSet<Point> = mutableSetOf()

        val maxX = lines[0].count() - 1
        val maxY = lines.count() - 1

        if (x > 0) neighbors.add(Pair(x-1,y))
        if (y > 0) neighbors.add(Pair(x,y-1))
        if (x < maxX) neighbors.add(Pair(x+1,y))
        if (y < maxY) neighbors.add(Pair(x,y+1))

        val newExplored = explored.plus(point).toMutableSet()
        val newBasin = mutableSetOf(point)

        neighbors.minus(explored).forEach { xPoint ->
            val (exploredBasin,exploredPoints) = getBasin(xPoint.first, xPoint.second, lines, newExplored)
            newExplored.addAll(exploredPoints)
            newBasin.addAll(exploredBasin)
        }

        return Pair(newBasin, newExplored)
    }

    fun part2(lines: List<String>): Long {
        val grid: List<List<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts()
        }

        val basins = mutableListOf<Basin>()
        val explored = mutableListOf<Point>()

        grid.forEachIndexed { y, line ->
            line.forEachIndexed { x, _ ->
                if (!explored.contains(Point(x, y))) {
                    //println("checking basin for $y, $x")
                    val (exploredBasin, exploredPoints) = getBasin(x, y, grid)
                    explored.addAll(exploredPoints)
                    if (exploredBasin.isNotEmpty()) {
                        basins.add(exploredBasin)
                    }
                }
            }
        }


        return basins.sortedByDescending { it.size }.take(3).fold(1L) { acc, r -> acc * r.size.toLong()}
    }


    val testInput = readInput("day09/test")

    check(part1(testInput) == 15)
    check(part2(testInput) == 1134L)

    val input = readInput("day09/input")
    println(part1(input))
    println(part2(input))
}

