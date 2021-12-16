package day10

import utils.readInput

fun main() {

    val pairs = mapOf(
        "(" to ")",
        "[" to "]",
        "{" to "}",
        "<" to ">",
    )
    val opens = pairs.keys

    val corruptedPoints = mapOf(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137,
    )

    val autocorrectPoints = mapOf(
        ")" to 1L,
        "]" to 2L,
        "}" to 3L,
        ">" to 4L,
    )

    fun getErrorPoints(line: String): Int {
        var openGroups = mutableListOf<String>()
        line.split("").filter { it != ""}.forEach { it ->
            if (opens.contains(it)) {
                openGroups.add(it)
            } else if (pairs[openGroups.last()] != it) {
                return corruptedPoints[it]!!
            } else {
                openGroups.removeLast()
            }
        }
        return 0
    }

    fun part1(lines: List<String>) = lines.sumOf { getErrorPoints(it) }



    fun getAutocorrectPoints(line: String): Long {
        var openGroups = mutableListOf<String>()
        line.split("").filter { it != ""}.forEach { it ->
            if (opens.contains(it)) {
                openGroups.add(it)
            } else if (pairs[openGroups.last()] == it) {
                openGroups.removeLast()
            } else {
                throw Exception("not expecting to see this in an incomplete line")
            }
        }
        return openGroups.reversed().fold(0L) { acc, g ->
            acc * 5L + autocorrectPoints[pairs[g]]!!
        }
    }

    fun part2(lines: List<String>): Long {
        val incompleteLines = lines.filter { getErrorPoints(it) == 0 }

        val scores = incompleteLines.map { getAutocorrectPoints(it) }.sorted()
        val middleIndex = (scores.size - 1) / 2
        return scores[middleIndex]
    }

    val testInput = readInput("day10/test")

    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10/input")
    println(part1(input))
    println(part2(input))
}

