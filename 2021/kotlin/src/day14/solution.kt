package day14

import utils.readInput
import utils.toInts
import java.util.*



fun main() {
    fun part1(lines: List<String>): Int {
        var polymer = lines[0]
        val rules = lines.subList(2, lines.size)
            .map { line -> line.split(" -> ") }
            .map { Pair(it[0], it[1]) }


        (0..9).forEach { _ ->
            val applicable = rules.filter { (find, _) -> polymer.contains(find) }
            val last = polymer.last()
            polymer = (0 until polymer.length-1).map { i ->
                val seq = polymer.subSequence(i, i+2)
                applicable.forEach { (f, r) ->
                    if (seq == f) {
                        return@map polymer[i] + r
                    }
                }
                polymer[i]
            }.joinToString("") + last
        }

        val counts = polymer.toList().groupBy { it }

        return counts.maxOf { it.value.size } - counts.minOf { it.value.size }
    }


    //TOOO - this won't work. java runs out of heap space. need a smarter approach.
    fun part2(lines: List<String>): Long {
        var polymer = lines[0]
        val rules = lines.subList(2, lines.size)
            .map { line -> line.split(" -> ") }
            .map { Pair(it[0], it[1]) }


        (0..39).forEach { _ ->
            val applicable = rules.filter { (find, _) -> polymer.contains(find) }
            val last = polymer.last()
            polymer = (0 until polymer.length-1).map { i ->
                val seq = polymer.subSequence(i, i+2)
                applicable.forEach { (f, r) ->
                    if (seq == f) {
                        return@map polymer[i] + r
                    }
                }
                polymer[i]
            }.joinToString("") + last
        }

        val counts = polymer.toList().groupBy { it }

        return counts.maxOf { it.value.size.toLong() } - counts.minOf { it.value.size.toLong() }
    }

    val testInput = readInput("day14/test")

    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("day13/input")
    println(part1(input))
    println(part2(input))
}

