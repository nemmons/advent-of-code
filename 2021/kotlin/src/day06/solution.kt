package day06

import utils.readInput

fun main() {

    fun part1(input: List<String>): Int {
        val fishPool = input[0].split(',').map { it.toInt() }.toMutableList()

        for (i in (1..80)) {
            for (f in fishPool.indices) {
                when(fishPool[f]) {
                    0 -> {
                        fishPool[f] = 6
                        fishPool.add(8)
                    }
                    else -> fishPool[f] -= 1
                }
            }
        }

        return fishPool.size
    }

    fun part2(input: List<String>): Long {
        val groupedFishPool: Map<Int, Long> = input[0].split(',').map { it.toInt() }.toMutableList().groupBy { it }
            .mapValues { it.value.size.toLong() }
            .plus(7 to 0)
            .plus(8 to 0)

        return (1..256).fold(groupedFishPool) { prev, _ ->
            mapOf(
                0 to (prev[1] ?: 0),
                1 to (prev[2] ?: 0),
                2 to (prev[3] ?: 0),
                3 to (prev[4] ?: 0),
                4 to (prev[5] ?: 0),
                5 to (prev[6] ?: 0),
                6 to (prev[0] ?: 0) + (prev[7] ?: 0),
                7 to (prev[8] ?: 0),
                8 to (prev[0] ?: 0),
            )
        }.map { it.value }.sum()
    }

    val testInput = readInput("day06/test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("day06/input")
    println(part1(input))
    println(part2(input))
}


