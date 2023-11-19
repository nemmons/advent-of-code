package year2022.day02

import utils.readInput
import utils.toInts
import java.lang.Integer.max

fun main() {

    val values = mapOf(
        'X' to 1,
        'Y' to 2,
        'Z' to 3
    )

    val winScore = 6
    val loseScore = 0
    val drawScore = 3

    val wins = mapOf(
        'X' to 'C',
        'Y' to 'A',
        'Z' to 'B'
    )

    val ties = mapOf(
        'X' to 'A',
        'Y' to 'B',
        'Z' to 'C'
    )

    fun doTheThing(input: List<String>): Int {
        return input.map { it.split(" ").run {
            Pair(this[0], this[1]) }
        }
            .fold(0) { total, pairz ->
                val myThrow = pairz.second.trim().toCharArray().first()
                val oppThrow = pairz.first.trim().toCharArray().first()
                total + values[myThrow]!! + when (oppThrow) {
                    wins[myThrow] -> {
                        winScore
                    }
                    ties[myThrow] -> {
                        drawScore
                    }
                    else -> loseScore
                }
            }
    }

    fun part1(input: List<String>): Int {
        return doTheThing(input)
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ").run {
            Pair(this[0], this[1]) }
        }
            .fold(0) { total, pairz ->
                val myResult = pairz.second.trim().toCharArray().first()
                val oppThrow = pairz.first.trim().toCharArray().first()

//                val myThrow = when(myResult) {
//                    'X' -> 1
//                    'Y' ->
//                    'Z' -> 3
//                }
                val myThrow = 'A'


                total + values[myThrow]!! + when (oppThrow) {
                    wins[myThrow] -> {
                        winScore
                    }
                    ties[myThrow] -> {
                        drawScore
                    }
                    else -> loseScore
                }
            }
    }

    val testInput = readInput("year2022/day02/test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("year2022/day02/input")
    println(part1(input))
    //println(part2(input))
}


