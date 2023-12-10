import java.time.LocalDateTime

private val cardStrengths = mutableMapOf(
    'A' to "14",
    'K' to "13",
    'Q' to "12",
    'J' to "11",
    'T' to "10",
    '9' to "09",
    '8' to "08",
    '7' to "07",
    '6' to "06",
    '5' to "05",
    '4' to "04",
    '3' to "03",
    '2' to "02",
)

private enum class HandType(val strength: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    PAIR(2),
    HIGH_CARD(1)
}

fun main() {
    fun part1(input: List<String>, useJokers: Boolean = false): Long {
        val handResults = input.map { line ->
            val (hand, bid) = line.trim().split(' ')

            val handType = getHandType(hand, useJokers)

            val handStrength =
                handType.strength.toString() +
                    cardStrengths[hand[0]]!!.toString() +
                    cardStrengths[hand[1]]!!.toString() +
                    cardStrengths[hand[2]]!!.toString() +
                    cardStrengths[hand[3]]!!.toString() +
                    cardStrengths[hand[4]]!!.toString()

           handStrength.toLong() to bid
        }.toMutableList()

        return handResults.sortedBy { it.first }
            .run { zip(1..(lastIndex+1)) }
            .sumOf { (hand, rank) ->
            rank.toLong() * hand.second.toLong()
        }

    }

    fun part2(input: List<String>): Long {
       return part1(input, true)
    }

    val testInput = readTestInput("Day07")
    //check(part1(testInput) == 6440L)

    val input = readInput("Day07")
    //part1(input).println()

    cardStrengths['J'] = "01"

    check(part2(testInput) == 5905L)

    val startTime = LocalDateTime.now()
    part2(input).println()
    val endTime = LocalDateTime.now()
    println(startTime)
    println(endTime)
}


private fun sortHand(hand: String) = hand
    .toMutableList()
    .sortedByDescending { cardStrengths[it] }
    .joinToString("")

private fun getHandType(hand: String, useJokers: Boolean): HandType {
    val freqs = hand.toList()
        .groupBy { it }
        .entries.map { (card, grouped) -> grouped.size to card }
        .sortedByDescending { it.first }
        .toMutableList()


    val jokerCount = if (useJokers) {
        freqs.firstOrNull { it.second == 'J' }?.first ?: 0
    } else 0
    if (useJokers) {
        freqs.firstOrNull { it.second == 'J' }?.let {
            freqs.remove(it)
            if (freqs.isEmpty()) {
                return HandType.FIVE_OF_A_KIND
            }
        }
    }



    if (freqs.first().first + jokerCount == 5) {
       return HandType.FIVE_OF_A_KIND
    }
    if (freqs.first().first + jokerCount == 4) {
        return HandType.FOUR_OF_A_KIND
    }

    if (freqs.first().first + jokerCount == 3) {
        if (freqs[1]!!.first == 2) {
            return HandType.FULL_HOUSE
        }

       return HandType.THREE_OF_A_KIND
    }

    if (freqs.first().first == 3) {
        if (freqs[1]!!.first == 1 && jokerCount == 1) {
            return HandType.FULL_HOUSE
        }

        return HandType.THREE_OF_A_KIND
    }

    val twoCount = freqs.count { it.first == 2 }
    if (twoCount == 2) {
        return HandType.TWO_PAIR
    } else if (twoCount == 1 && jokerCount == 1) {
        return HandType.TWO_PAIR
    } else if (twoCount == 1) {
        return HandType.PAIR
    } else if (twoCount == 0 && jokerCount == 1) {
        return HandType.PAIR
    }

    if (freqs.count { it.first == 1 } < 5 || jokerCount > 0) {
        throw Exception("Got hand $hand wrong try again!")
    }

    return HandType.HIGH_CARD

}
