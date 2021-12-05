package day05

import utils.readInput
import java.lang.Integer.min

val regex = """^(\d+),(\d+) -> (\d+),(\d+)$""".toRegex()

fun main() {

    fun part1(input: List<String>): Int {
        return input.toLines()
            .filter { it.isStraightLine() }
            .countOverlappingPoints()

    }

    fun part2(input: List<String>): Int {
        return input.toLines()
            .countOverlappingPoints()
    }

    val testInput = readInput("day05/test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day05/input")
    println(part1(input))
    println(part2(input))
}

data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    fun isStraightLine() : Boolean {
        return x1 == x2 || y1 == y2
    }

    fun points(): List<Pair<Int,Int>> {
        if (x1 == x2) {
            return (min(y1,y2)..y1.coerceAtLeast(y2)).map { y -> Pair(x1, y) }
        } else if (y1 == y2) {
            return (min(x1,x2)..x1.coerceAtLeast(x2)).map { x -> Pair(x, y1) }
        } else {

            val leftMostPoint = if (x1 <= x2) { Pair(x1,y1) } else { Pair(x2, y2) }
            val rightMostPoint = if (x1 <= x2) { Pair(x2,y2) } else { Pair(x1, y1) }
            val topMostPoint = if (y1 <= y2) { Pair(x1, y1) } else { Pair(x2, y2) }

            //most from leftmost point to rightmost point - see if we need to go up or down
            val yDirectionModifier = if (leftMostPoint == topMostPoint) 1 else -1

            var x = leftMostPoint.first
            var y = leftMostPoint.second
            val points = mutableListOf<Pair<Int, Int>>()

            while (x != rightMostPoint.first && y != rightMostPoint.second) {
                points.add(Pair(x,y))
                x += 1
                y += 1 * yDirectionModifier
            }
            points.add(rightMostPoint)

            return points
        }
    }
}

fun List<String>.toLines() : List<Line> {
    return this.map {
        val match = regex.find(it)
        val (x1, y1, x2, y2) = match!!.destructured
        Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
    }
}

fun List<Line>.countOverlappingPoints() : Int {
    return this.flatMap { it.points() }
        .groupBy { it }
        .count { it.value.size > 1}
}
