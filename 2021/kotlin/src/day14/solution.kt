package day14

import utils.readInput
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


    //Solved with a hint from Reddit (counting pairs rather than building and mutating the polymer string).
    fun part2(lines: List<String>): Long {
        var polymer = lines[0]
        val rules = lines.subList(2, lines.size)
            .map { line -> line.split(" -> ") }
            .map { Pair(it[0], it[1]) }

        //calculate initial state of pairs from input
        var pairs = mutableMapOf<String, Long>()
        (0..polymer.length-2).forEach { i ->
            val pair: String = polymer[i].toString() + polymer[i+1].toString()
            pairs.putIfAbsent(pair, 0)
            pairs[pair] = pairs[pair]!! + 1
        }

        (0..39).forEach { _ ->
            //calculate the diffs for this iteration
            var pairDiff = mutableMapOf<String, Long>()
            rules.filter { pairs.containsKey(it.first) }.forEach { (find, replace) ->
                val count: Long = pairs[find]!!
                pairDiff.putIfAbsent(find, 0)
                pairDiff[find] = pairDiff[find]!! - count

                val new1 = find[0] + replace
                pairDiff.putIfAbsent(new1, 0)
                pairDiff[new1] = pairDiff[new1]!! + count

                val new2 = replace + find[1]
                pairDiff.putIfAbsent(new2, 0)
                pairDiff[new2] = pairDiff[new2]!! + count
            }

            //apply the diffs
            pairDiff.forEach { newPair ->
                pairs.putIfAbsent(newPair.key, 0)
                pairs[newPair.key] = pairs[newPair.key]!! + newPair.value
            }
        }

        //count the letters across all pairs
        val counts = mutableMapOf<Char, Long>()
        pairs.forEach { pair ->
            counts.putIfAbsent(pair.key[0], 0)
            counts[pair.key[0]] = counts[pair.key[0]]!! + pair.value
            counts.putIfAbsent(pair.key[1], 0)
            counts[pair.key[1]] = counts[pair.key[1]]!! + pair.value
        }
        val goodCounts = counts.mapValues { it.value / 2 }.toMutableMap() //since each letter is counted in two pairs
            .also {
                //offset the division by 2 for the first and last letter, since they would each only be in 1 pair
                val first = polymer[0]
                val last = polymer.last()

                it[first] = it[first]!! + 1
                it[last] = it[last]!! + 1
            }

        return goodCounts.values.maxOrNull()!! - goodCounts.values.minOrNull()!!
    }

    val testInput = readInput("day14/test")

    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("day13/input")
    println(part1(input))
    println(part2(input))
}

