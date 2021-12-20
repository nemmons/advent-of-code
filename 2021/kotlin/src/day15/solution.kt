package day15

import utils.readInput
import utils.toInts
import java.util.*



fun main() {

    fun calcRisk(grid: List<List<Int>>, runningRisk: Int, x: Int, y: Int): List<Int> {
        val newRisk = if (y > 0 || x > 0) {
            grid[y][x]
        } else { 0 }

        if (y == grid.size - 1 && x == grid[0].size - 1) {
            return listOf(runningRisk + newRisk)
        }

        val dests = mutableListOf<Pair<Int,Int>>()

        if (y < grid.size - 1) {
            dests.add(Pair(x,y+1))
        }
        if (x < grid[0].size - 1) {
            dests.add(Pair(x+1,y))
        }

        return dests.flatMap {
            calcRisk(grid, runningRisk + newRisk, it.first, it.second)
        }
    }

    fun part1(lines: List<String>): Int {
        val grid: List<List<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts()
        }

        return calcRisk(grid, 0, 0, 0).minOrNull()!!
    }


    fun part2(lines: List<String>): Int {
        return 2
    }

    val testInput = readInput("day15/test")

    check(part1(testInput) == 40)
    check(part2(testInput) == 2)

    val input = readInput("day15/input")
    println(part1(input))
    println(part2(input))
}

