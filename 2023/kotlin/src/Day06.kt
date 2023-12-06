import java.time.LocalDateTime

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].split(':')[1]
            .trim()
            .split(' ')
            .filterNot { it.isEmpty() }
            .map { it.trim().toInt() }

        val distances = input[1].split(':')[1]
            .trim()
            .split(' ')
            .filterNot { it.isEmpty() }
            .map { it.trim().toInt() }

        if (times.size != distances.size) { throw Exception("uh oh, you're in trouble!") }

        val races = times.zip(distances)

        return races.map { (raceTime, distanceGoal) ->
            (1.until(raceTime)).count { holdTime ->
                distance(holdTime, raceTime) > distanceGoal
            }
        }.reduce { acc, i ->  acc * i }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split(':')[1]
            .trim()
            .replace(" ","")
            .run { trim().toLong() }

        val distance = input[1].split(':')[1]
            .trim()
            .replace(" ","")
            .run { trim().toLong() }

        return (1.until(time)).count { holdTime ->
            distance(holdTime, time) > distance
        }
    }

    val testInput = readTestInput("Day06")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()

    val startTime = LocalDateTime.now()
    part2(input).println()
    val endTime = LocalDateTime.now()
    println(startTime)
    println(endTime)
}


fun distance(holdTime: Int, raceTime: Int): Int {
    return holdTime * (raceTime - holdTime)
}

fun distance(holdTime: Long, raceTime: Long): Long {
    return holdTime * (raceTime - holdTime)
}
