package year2022.day04

import utils.readInput
import utils.toInts
import java.lang.Integer.max

fun main() {

    val re = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()


    fun doTheThing(input: List<String>): Int {
        return input.count { line ->
            val parsed = re.find(line)
            val (l1, l2, r1, r2) = parsed!!.destructured

            println(line)
            println(listOf(l1, l2, r1, r2))

            val res = (l1 <= r1 && l2 >= r2)
                    || (l2 <= r2 && l1 >= r1)
            res.also { println(it)}
        }
    }

    fun part1(input: List<String>): Int {
        return doTheThing(input)
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("year2022/day04/test")
    check(part1(testInput) == 2)
//    check(part2(testInput) == 70)

    val input = readInput("year2022/day04/input")
    println(part1(input))
//    println(part2(input))
}


