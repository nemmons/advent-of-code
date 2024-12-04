import java.time.LocalDateTime


fun main() {
    fun calcDiffs(line: List<Long>): MutableList<Long> {
        return (1.until(line.size)).map { i ->
            line[i] - line[i-1]
        }.toMutableList()
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val diffs = mutableListOf(
                line.trim().split(' ').map { it.toLong() }.toMutableList()
            )

            while(diffs.last().any {it != 0L}) {
                diffs.add(calcDiffs(diffs.last()))
            }

            diffs.last().add(0)

            (diffs.lastIndex downTo 1).forEach { row ->
                diffs[row - 1].add(diffs[row].last() + diffs[row-1].last())
            }

            diffs.first().last()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val diffs = mutableListOf(
                line.trim().split(' ').map { it.toLong() }.toMutableList()
            )

            while (diffs.last().any { it != 0L }) {
                diffs.add(calcDiffs(diffs.last()))
            }

            diffs[diffs.lastIndex].add(0, 0L)


            (diffs.lastIndex downTo 1).forEach { row ->
                diffs[row - 1].add(0, diffs[row].first() * -1  + diffs[row - 1].first())
            }

            diffs.first().first()
        }
    }


    val testInput = readTestInput("Day09")
    check(part1(testInput) == 114L)


    val input = readInput("Day09")
    part1(input).println()


    check(part2(testInput) == 2L)


    val startTime = LocalDateTime.now()
    part2(input).println()
    val endTime = LocalDateTime.now()
    println(startTime)
    println(endTime)
}

