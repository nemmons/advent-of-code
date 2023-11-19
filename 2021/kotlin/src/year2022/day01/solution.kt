package year2022.day01

import utils.readInput
import utils.toInts
import java.lang.Integer.max

fun main() {

    fun countCalories(input: List<String>): List<Int> {
        var runningCount = 0
        val elfCalories = mutableListOf<Int>()

        val ans1 = input.fold(Pair(0,0)) { acc, str ->
            var maxVal = acc.first
            var runningVal = acc.second

            if (str.isBlank()) {
                maxVal = max(maxVal, runningVal)
                runningVal = 0
            }
            else {
                runningVal += str.toInt()
            }
            Pair(maxVal, runningVal)
        }
        println("New Guess is ${ans1.first}")


        input.forEach { line ->
            if (line.isBlank()) {
                elfCalories.add(runningCount)
                runningCount = 0
            }
            else { runningCount += line.toInt() }
        }
        elfCalories.add(runningCount)
        return elfCalories
    }


    fun part1(input: List<String>): Int {
        return countCalories(input).maxOrNull()!!
    }

    fun part2(input: List<String>): Int {
        val list = countCalories(input)
        println(list)
        println(list.sortedDescending())
        return list.sortedDescending().take(3).sum()
    }

    val testInput = readInput("year2022/day01/test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("year2022/day01/input")
    println(part1(input))
    println(part2(input))
}


