package year2022.day03

import utils.readInput
import utils.toInts
import java.lang.Integer.max

fun main() {

    fun doTheThing(input: List<String>): Int {
        return input.sumOf { line ->
            val dup = line.toList().chunked(line.length / 2).let {
                it[0].intersect(it[1].toSet())
            }.firstOrNull()!!

            when(dup.code) {
                in 97..122 -> dup.code - 96
                in 65..90 -> dup.code - 64 + 26
                else -> throw Exception("Unexpected code for $dup")
            }
        }
    }

    fun part1(input: List<String>): Int {
        return doTheThing(input)
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf {
                val badge = it[0].toSet().intersect(it[1].toSet())
                    .intersect(it[2].toSet()).first()

                when(badge.code) {
                    in 97..122 -> badge.code - 96
                    in 65..90 -> badge.code - 64 + 26
                    else -> throw Exception("Unexpected code for $badge")
                }
            }

    }

    val testInput = readInput("year2022/day03/test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("year2022/day03/input")
    println(part1(input))
    println(part2(input))
}


