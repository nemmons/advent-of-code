package day03

import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val commonUncommonBitPairs = (1..input[0].length).map { index ->
            val grouped = input.groupBy { str -> str[index-1] }
            val min: Char = grouped.minByOrNull { bitCount -> bitCount.value.size }!!.key
            val max: Char = grouped.maxByOrNull { bitCount -> bitCount.value.size }!!.key
            Pair(min.toString(), max.toString())
        }

        val epsilonString = commonUncommonBitPairs.fold("") { acc, pair -> "$acc${pair.first}"}
        val gammaString = commonUncommonBitPairs.fold("") { acc, pair -> "$acc${pair.second}"}

        val epsilon = Integer.parseInt(epsilonString, 2)
        val gamma = Integer.parseInt(gammaString, 2)

        return epsilon * gamma
    }

    //todo: Learn some binary and clean this dumpster fire up
    fun part2(input: List<String>): Int {
        var remainingInputForO2GenRating = input.toList()
        var remainingInputForCO2ScubberRating = input.toList()

        for (i in 1..input[0].length) {
            val mostCommonBit = remainingInputForO2GenRating.groupBy { it[i-1] }.let {
                if (it['0']?.size == it['1']?.size) {
                    '1'
                } else {
                    it.maxByOrNull { bitCount -> bitCount.value.size }!!.key
                }
            }
            val leastCommonBit = remainingInputForCO2ScubberRating.groupBy { it[i-1] }.let {
                if (it['0']?.size == it['1']?.size) {
                    '0'
                } else {
                    it.minByOrNull { bitCount -> bitCount.value.size }!!.key
                }
            }

            remainingInputForO2GenRating = remainingInputForO2GenRating.filter { it[i-1] == mostCommonBit }
            remainingInputForCO2ScubberRating = remainingInputForCO2ScubberRating.filter { it[i-1] == leastCommonBit }
        }

        val o2GenRating = Integer.parseInt(remainingInputForO2GenRating.first(), 2)
        val co2ScrubberRating = Integer.parseInt(remainingInputForCO2ScubberRating.first(), 2)

        return o2GenRating * co2ScrubberRating
    }

    val testInput = readInput("day03/test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day03/input")
    println(part1(input))
    println(part2(input))
}



