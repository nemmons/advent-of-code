package day17

import utils.readInput
import java.lang.Integer.max

val inputPattern = """target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""".toRegex()

data class Target(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {
    fun contains(x: Int, y: Int): Boolean {
        return (x in x1..x2) && (y in y1..y2)
    }
}

/***
 *
 * x0 = 0 dx = 6
 * x1 = 6 dx = 5
 * x2 = 11 dx = 4
 * x3 = 15 dx = 3
 * x4 = 18 dx = 2
 * x5 = 20 dx = 1
 * x6 = 21 dx = 0
 */

data class Probe(
    var dx: Int,
    var dy: Int,
    var x: Int = 0,
    var y: Int = 0,
    var maxY: Int = 0,
    var time: Int = 0
    ) {
    fun move() {
        x += dx
        y += dy
        dx += (dx.compareTo(0) * -1) //move towards 0
        dy -= 1
        maxY = max(y, maxY)
        time += 1
    }

    fun checkTarget(target: Target): Boolean {
        return target.contains(x, y)
    }
}

//fun timeToTargetX(x1: Int, x2: Int, dx: Int): List<Int> {
//    val times = listOf<Int>()
//
//    val t0 = when(dx.compareTo(x1 / dx)) {
//        -1 ->
//        0 ->
//        1 ->
//    }
//}

fun main() {

    fun part1(input: String): Int {
        val results = inputPattern.find(input)
        val (x1,  x2, y1, y2) = results!!.destructured
        return 1
    }


    fun part2(line: String): Int {
        return 2
    }

    val testInput = readInput("day17/test")

    check(part1(testInput[0]) == 45)
    check(part2(testInput[0]) == 2)

    val input = readInput("day17/input")
    println(part1(input[0]))
    println(part2(input[0]))
}
