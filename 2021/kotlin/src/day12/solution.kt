package day12

import utils.readInput
import utils.toInts
import java.util.*


data class Cave(val name: String, val connections: MutableList<String> = mutableListOf()) {
    val isSmall = name.lowercase(Locale.getDefault()) == name && !listOf("start", "end").contains(name)

    fun traverse(allCaves: Map<String, Cave>, path: MutableList<Cave> = mutableListOf(), smallCaveTravelCount: Int): List<MutableList<Cave>> {
        val caves = connections.map { allCaves[it]!! }

        if (name == "end") {
            return listOf(path.toMutableList().also{it.add(this)})
        }

        //todo - refactor this. it was so much cleaner before part 2!
        val usedSmallCounts = path.plus(this).filter { allCaves[it.name]!!.isSmall }
            .groupBy { it.name }
            .mapValues { it.value.count() }

        val usedSmalls = if (smallCaveTravelCount == 2 && usedSmallCounts.values.contains(2)) {
             path.filter { allCaves[it.name]!!.isSmall }
        } else if (smallCaveTravelCount == 1) {
            path.filter { allCaves[it.name]!!.isSmall }
        } else {
            listOf()
        }
        //

        val validCaves = caves.minus(usedSmalls).minus(allCaves["start"]!!)

        return validCaves.flatMap { nextCave ->
            nextCave.traverse(allCaves, path.toMutableList().also{it.add(this)}, smallCaveTravelCount)
        }
    }
}




fun main() {
    fun part1(lines: List<String>): Int {

        val caves = mutableMapOf<String, Cave>()

        lines.forEach { line ->
            val (first, second) = line.split('-')
            caves.putIfAbsent(first, Cave(first))
            caves.putIfAbsent(second, Cave(second))
            caves[first]!!.connections.add(second)
            caves[second]!!.connections.add(first)
        }

        val start = caves["start"]!!

        val paths = start.traverse(caves, mutableListOf(), 1)
        return paths.count()
    }


    fun part2(lines: List<String>): Int {

        val caves = mutableMapOf<String, Cave>()

        lines.forEach { line ->
            val (first, second) = line.split('-')
            caves.putIfAbsent(first, Cave(first))
            caves.putIfAbsent(second, Cave(second))
            caves[first]!!.connections.add(second)
            caves[second]!!.connections.add(first)
        }

        val start = caves["start"]!!

        val paths = start.traverse(caves, mutableListOf(), 2)
        return paths.count()

    }

    val testInput = readInput("day12/test")
    val testInput2 = readInput("day12/test2")

    check(part1(testInput) == 19)
    check(part1(testInput2) == 226)
    check(part2(testInput) == 103)
    check(part2(testInput2) == 3509)

    val input = readInput("day12/input")
    println(part1(input))
    println(part2(input))
}

