
fun main() {
    fun part1(input: List<String>): Int {
        val limits = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        return input.map { game ->
            val (label, results) = game.split(':')
            label to results
        }.filter { (_, results) ->
            // only keep games with no bad draws
            results.trim().split(';').none { round ->
                // find any 'bad draws' (that is, a draw that couldn't actually happen based on the known contents of the bag
                round.trim().split(',').any { draw ->
                    val (num, color) = draw.trim().split(' ')
                    num.toInt() > limits[color]!!
                }
            }
        }.map { (label, _) ->
            label.replace("Game ","").toInt()
        }.reduce { acc, num ->
            acc + num
        }
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
