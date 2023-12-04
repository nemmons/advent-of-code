import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Float {
        var points = 0f

        input.forEach { line ->
            val (winningNumbers, myNumbers) = line.split(':')[1].trim().split('|')

            val overlaps = myNumbers.trim().split(' ').filterNot { it.isEmpty() }.toSet().intersect(
                winningNumbers.trim().split(' ').filterNot { it.isEmpty() }.toSet()
            )

            if (overlaps.isNotEmpty()) {
                points += 2f.pow(overlaps.size - 1)
            }
        }

        return points
    }

    fun part2(input: List<String>): Int {
        val cardCounts = mutableMapOf<Int, Int>()

        (input.indices).forEach { j ->
            cardCounts[j + 1] = 1
        }

        input.forEachIndexed { row, line ->
            val cardNumber = row + 1

            val (winningNumbers, myNumbers) = line.split(':')[1].trim().split('|')

            val overlaps = myNumbers.trim().split(' ').filterNot { it.isEmpty() }.toSet().intersect(
                winningNumbers.trim().split(' ').filterNot { it.isEmpty() }.toSet()
            )

            if (overlaps.isNotEmpty()) {
                overlaps.indices.forEach { offset ->
                    val targetRow = cardNumber + offset + 1
                    if (targetRow <= cardCounts.size) {
                        cardCounts[targetRow] = cardCounts[targetRow]!! + cardCounts[cardNumber]!!
                    }
                }
            }
        }
        return cardCounts.values.reduce{ sum, num -> sum + num }
    }

    val testInput = readTestInput("Day04")
    check(part1(testInput) == 13f)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

