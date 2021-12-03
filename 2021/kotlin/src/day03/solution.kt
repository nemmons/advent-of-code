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

        val epsilon = commonUncommonBitPairs
            .joinToString("") { it.first }
            .let { Integer.parseInt(it, 2) }
        val gamma =  commonUncommonBitPairs
            .joinToString("") { it.second }
            .let { Integer.parseInt(it, 2) }

        return epsilon * gamma
    }

    //TODO - there's way more room to abstract out what's happening below here
    fun part2(input: List<String>): Int {
        var remainingInputForO2GenRating = input.toList()
        var remainingInputForCO2ScubberRating = input.toList()

        for (i in input[0].indices) {
            if (remainingInputForO2GenRating.size > 1) {
                val o2ZerosCount = remainingInputForO2GenRating
                    .map { it[i] }.filter { it == '0'}.count()
                val o2OnesCount = remainingInputForO2GenRating.size - o2ZerosCount
                val mostCommonBit = if (o2OnesCount >= o2ZerosCount) '1' else '0'
                remainingInputForO2GenRating = remainingInputForO2GenRating.filter { it[i] == mostCommonBit }
            }

            if (remainingInputForCO2ScubberRating.size > 1) {
                val co2ZerosCount = remainingInputForCO2ScubberRating
                    .map { it[i] }.filter { it == '0'}.count()
                val co2OnesCount = remainingInputForCO2ScubberRating.size - co2ZerosCount
                val leastCommonBit = if (co2OnesCount >= co2ZerosCount) '0' else '1'
                remainingInputForCO2ScubberRating = remainingInputForCO2ScubberRating.filter { it[i] == leastCommonBit }
            }
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



