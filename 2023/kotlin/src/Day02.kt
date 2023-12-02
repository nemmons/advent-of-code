
fun main() {
    fun part1(input: List<String>): Int {
        val red = 12
        val green = 13
        val blue = 14

        var sum = 0
        input.forEach { line ->
            val (label, results) = line.split(':')

            val badGames = results.trim().split(';').filter { round ->
                round.trim().split(',').any { draw ->
                    val (num, color) = draw.trim().split(' ')
                    when (color) {
                        "red" -> num.toInt() > red
                        "green" -> num.toInt() > green
                        "blue" -> num.toInt() > blue
                        else -> throw Exception("unexpected color $color")
                    }
                }
            }

            if (badGames.isEmpty()) {
                sum += label.replace("Game ", "").toInt()
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        input.forEach { line ->
            val (_, results) = line.split(':')

            val requirements = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )

            results.trim().split(';').forEach { round ->
                round.trim().split(',').forEach { draw ->
                    val (num, color) = draw.trim().split(' ')

                    requirements[color] = maxOf(requirements[color] ?: 0, num.toInt())
                }
            }

            sum += requirements.values.reduce{ acc, num -> acc * num }
        }

        return sum
    }

    val testInput = readTestInput("Day02")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
