fun main() {
    fun part1(input: List<String>): Int {
        val regex = "\\d".toRegex()

        return input.map { line ->
            regex.findAll(line)
                .map { it.value }
                .let { "${it.first()}${it.last()}".toInt() }
        }.reduce { acc, num -> acc + num }

    }

    fun part2(input: List<String>): Int {
        //use lookahead to prevent matches from being consumed (i.e. otherwise, in 'oneight' consuming the 'one' prevents us from finding the 'eight')
        val regex = "(?=(\\d|one|two|three|four|five|six|seven|eight|nine))".toRegex()

        return input.map { line ->
            regex.findAll(line)
                .map {
                    it.groups[1]!!.value
                }
                .let { "${it.first().numVal()}${it.last().numVal()}".toInt() }
        }.reduce { acc, num -> acc + num }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun String.numVal(): Int {
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
