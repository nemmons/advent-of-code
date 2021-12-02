package day01

import utils.readInput
import utils.toInts

fun main() {
    fun countIncreases(input: List<Int>): Int {
        return input
            .zipWithNext()
            .filter { pair -> pair.second > pair.first }
            .count()
    }

    fun part1(input: List<Int>): Int {
        return countIncreases(input)
    }

    fun part2(input: List<Int>): Int {
        return input
            .windowed(3, 1) { it.sum() }
            .let { countIncreases(it) }
    }

    val testInput = readInput("day01/test")
    check(part1(testInput.toInts()) == 7)
    check(part2(testInput.toInts()) == 5)

    val input = readInput("day01/input")
    println(part1(input.toInts()))
    println(part2(input.toInts()))
}


