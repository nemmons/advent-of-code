private val validTokensPart1 = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
private val validTokensPart2 = listOf(
    "1", "2", "3", "4", "5", "6", "7", "8", "9",
    "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
)


fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            it.parseTokens(validTokensPart1)
                .run {
                    this.first().toInt() * 10 + this.last().toInt()
                }
        }.reduce { acc, num -> acc + num }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            it.parseTokensBetter(validTokensPart2)
                .let { tokens ->
                    tokens.first().numVal() * 10 + tokens.last().numVal()
                }
        }.reduce { acc, num -> acc + num }
    }

    val testInput = readTestInput("Day01")
    check(part1(testInput) == 142)

    val testInput2 = readTestInput("Day01_2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun String.parseTokens(validTokens: List<String>): List<String> {
    val tokens = mutableListOf<String>()
    for (i in this.indices) {
        val peek = this.substring(i, i + 1)
        if (validTokens.contains(peek)) {
            tokens.add(peek)
        }
    }
    return tokens
}

private fun String.parseTokensBetter(validTokens: List<String>): List<String> {
    val tokens = mutableListOf<String>()

    var i = 0
    while (i < this.length) {
        for (j in listOf(0,2,3,4)) { // the 2..4 range is because i know all the string tokens are 3-5 chars long
            if (validTokens.contains(this.substring(i, minOf(i + j + 1, this.length)))) {
                tokens.add(this.substring(i, i + j + 1))
                i += maxOf(j - 1, 0) //skip ahead a bit but allow one char for overlap for 'oneight', 'twone', etc
                break
            }
        }
        i++
    }

    return tokens
}

private fun String.numVal(): Int {
    return when (this) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> this.toInt()
    }
}
