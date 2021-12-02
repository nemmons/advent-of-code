package day02

import utils.readInput

val regex = """^([A-Za-z]+) (\d+)${'$'}""".toRegex()

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val match = regex.find(line)
            val (direction, quantity) = match!!.destructured
            when (direction) {
                "forward" -> Pair(quantity.toInt(), 0)
                "up" -> Pair(0, quantity.toInt() * -1)
                "down" -> Pair(0, quantity.toInt())
                else -> throw Exception("Unrecognized Direction")
            }
        }.reduce { p1, p2 -> Pair(p1.first + p2.first, p1.second + p2.second) }
            .let { it.first * it.second }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val match = regex.find(line)
            val (direction, amount) = match!!.destructured
            Pair(direction, amount)
        }.fold(Submarine()) { submarine, (direction, amount) ->
            when (direction) {
                "up" -> submarine.up(amount.toInt())
                "down" -> submarine.down(amount.toInt())
                "forward" -> submarine.forward(amount.toInt())
                else -> throw Exception("Unrecognized Direction")
            }
            submarine
        }.let {
            it.depth * it.pos
        }
    }

    val testInput = readInput("day02/test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("day02/input")
    println(part1(input))
    println(part2(input))
}


data class Submarine(
    var aim: Int = 0,
    var pos: Int = 0,
    var depth: Int = 0
) {
    fun up(amount: Int) {
        aim -= amount
    }

    fun down(amount: Int) {
        aim += amount
    }

    fun forward(amount: Int) {
        pos += amount
        depth += amount * aim
    }
}


