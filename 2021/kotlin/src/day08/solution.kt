package day08

import utils.readInput

private val digitSegmentCounts = mapOf(
    0 to 6,
    1 to 2,
    2 to 5,
    3 to 5,
    4 to 4,
    5 to 5,
    6 to 6,
    7 to 3,
    8 to 7,
    9 to 6
)
private val uniqueSegmentCounts = digitSegmentCounts.values.groupBy { it }.filter { it.value.count() == 1}.keys
private val segments = mapOf(
    '0' to setOf('a', 'b', 'c', 'e', 'f', 'g'),
    '1' to setOf('c', 'f'),
    '2' to setOf('a', 'c', 'd', 'e', 'g'),
    '3' to setOf('a', 'c', 'd', 'f', 'g'),
    '4' to setOf('b', 'c', 'd', 'f'),
    '5' to setOf('a', 'b', 'd', 'f', 'g'),
    '6' to setOf('a', 'b', 'd', 'e', 'f', 'g'),
    '7' to setOf('a', 'c', 'f'),
    '8' to setOf('a', 'b', 'c', 'd', 'e', 'f', 'g'),
    '9' to setOf('a', 'b', 'c', 'd', 'f', 'g'),
)


fun main() {

    fun part1(lines: List<String>): Int {
        return lines.map { line ->
            val lineOutput = line.split('|')[1]
            lineOutput.trim().split(' ').filter { seq ->
                uniqueSegmentCounts.contains(seq.length)
            }.count()
        }.sum()
    }

    fun inferSegmentMapping(input: String): Map<Char, Char> {
        val results = mutableMapOf<Char,Char>()
        val sequences = input.split(' ')

        //find based on unique counts
        val one = sequences.first { it.length == 2 }.toSet()
        val four = sequences.first { it.length == 4 }.toSet()
        val seven = sequences.first { it.length == 3 }.toSet()
        val eight = sequences.first { it.length == 7 }.toSet()

        val three = sequences.first {
            it.length == 5 && it.toSet().containsAll(one)
        }.toSet()
        val nine = sequences.first {
            it.length == 6 && it.toSet().containsAll(four)
        }.toSet()

        results['a'] = seven.subtract(one).first()
        results['b'] = four.subtract(three).first()

        val five = sequences.first {
            it.length == 5 && it.contains(results['b']!!)
        }.toSet()

        results['c'] = one.subtract(five).first()
        results['d'] = four.subtract(one).subtract(setOf(results['b']!!)).first()
        results['e'] = eight.subtract(nine).first()
        results['f'] = one.subtract(setOf(results['c']!!)).first()
        results['g'] = eight.subtract(seven).subtract(four).subtract(setOf(results['e']!!)).first()

        return results
    }

    fun mapped(mapping: Map<Char, Char>): Map<Char, Set<Char>> {
        return segments.mapValues {
            it.value.map { char ->
                mapping[char]!!
            }.toSet()
        }
    }

    fun evaluateOutput(output: String, mapping: Map<Char, Char>): Int {
        return output.trim().split(' ')
            .map { sequence ->
            mapped(mapping).filter { entry ->
                entry.value == sequence.toSet()
            }.keys.first()
        }.joinToString("").toInt()
    }

    fun part2(lines: List<String>): Int {
        return lines.map { line ->
            val split = line.split('|')
            val input = split[0]
            val output = split[1]
            val mapping = inferSegmentMapping(input)
            evaluateOutput(output, mapping)
        }.sum()
    }



    val testInput = readInput("day08/test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("day08/input")
    println(part1(input))
    println(part2(input))
}
