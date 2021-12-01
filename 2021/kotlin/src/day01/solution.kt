package day01

import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }
            .zipWithNext()
            .filter { pair -> pair.second > pair.first }
            .count()
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }
            .windowed(3, 1) { it.sum() }
            .zipWithNext()
            .filter { pair -> pair.second > pair.first }
            .count()
    }

    val testInput = readInput("day01/test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01/input")
    println(part1(input))
    println(part2(input))
}
