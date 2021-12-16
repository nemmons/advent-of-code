package day11

import utils.readInput
import utils.toInts

fun main() {

    fun List<MutableList<Int>>.up() {
        this.forEach { line ->
            line.indices.forEach { x -> line[x] += 1 }
        }
    }

    fun List<MutableList<Int>>.shouldFlash(alreadyFlashed: Set<Pair<Int,Int>>): Boolean {
        this.forEachIndexed { y, line ->
            line.forEachIndexed { x, value ->
                if (value > 9 && !alreadyFlashed.contains(Pair(x,y))) {
                    return true
                }
            }
        }

        return false
    }

    fun List<MutableList<Int>>.flash(): Int {
        val flashed = mutableSetOf<Pair<Int, Int>>()

        val maxX = this[0].count() - 1
        val maxY = this.count() - 1

        while (this.shouldFlash(flashed)) {
            this.forEachIndexed { y, line ->
                line.forEachIndexed { x, value ->
                    if (value > 9 && !flashed.contains(Pair(x,y))) {
                        flashed.add(Pair(x, y))

                        //increase the energy level of any neighbors
                        if (x > 0) this[y][x - 1] += 1
                        if (y > 0) this[y - 1][x] += 1
                        if (x < maxX) this[y][x + 1] += 1
                        if (y < maxY) this[y + 1][x] += 1
                        if (x > 0 && y > 0) this[y-1][x-1] += 1
                        if (x < maxX && y > 0) this[y-1][x+1] += 1
                        if (x < maxX && y < maxY) this[y+1][x+1] += 1
                        if (x > 0 && y < maxY) this[y+1][x-1] += 1
                    }
                }
            }
        }

        //reset the flashed octopi to 0 energy
        flashed.forEach { this[it.second][it.first] = 0 }

        return flashed.size
    }

    fun part1(lines: List<String>):Int {
        var flashes = 0
        val octoGrid: List<MutableList<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts().toMutableList()
        }

        (1..100).forEach { _ ->
            octoGrid.up()
            flashes += octoGrid.flash()
        }

        return flashes
    }


    fun part2(lines: List<String>):Int {
        var step = 1
        val octoGrid: List<MutableList<Int>> = lines.map {
            it.split("").filter { char -> char != "" }.toInts().toMutableList()
        }

        while(true) {
            octoGrid.up()
            val flashCount = octoGrid.flash()
            if (flashCount == octoGrid.size * octoGrid[0].size) {
                return step
            }
            step += 1
        }

    }

    val testInput = readInput("day11/test")

    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("day11/input")
    println(part1(input))
    println(part2(input))
}

