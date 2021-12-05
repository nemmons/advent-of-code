package day05

import utils.readInput

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
        //Check which direction we need to move to get from point 1 to point 2
        val dx = x1.compareTo(x2) * -1
        val dy = y1.compareTo(y2) * -1

        var x = x1
        var y = y1
        val points = mutableListOf<Pair<Int, Int>>()

        while (!(x == x2 && y == y2)) {
            points.add(Pair(x,y))
            x += dx
            y += dy
        }
        points.add(Pair(x2,y2))

        return points
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
