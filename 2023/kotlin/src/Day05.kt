import java.time.LocalDateTime

fun main() {
    val labelRegex = "([a-z]+)-to-([a-z]+) map:".toRegex()

    fun part1(input: List<String>): Long {
        val (_, seedStr) = input[0].split(':')
        val seeds = seedStr.trim().split(' ').map { it.toLong() }

        var (fromLbl, toLbl) = "" to ""

        val maps = mutableMapOf<Pair<String, String>, MutableMap<Pair<Long, Long>, Pair<Long, Long>>>()

        input.subList(2, input.size - 1).forEach { line ->
            if (labelRegex.matches(line)) {
                val matches = labelRegex.find(line)!!
                fromLbl = matches.groupValues[1]
                toLbl = matches.groupValues[2]
                maps.putIfAbsent(fromLbl to toLbl, mutableMapOf())
            } else if (line.trim().isNotEmpty()) {
                val (toStart, fromStart, rangeLength) = line.trim().split(' ').map { it.toLong() }

                maps[fromLbl to toLbl]!![fromStart to fromStart + rangeLength] = toStart to toStart + rangeLength
            }
        }

        return seeds.minOf { seed ->
            var mappedSeedVal = seed
            var from = "seed"
            var to: String

            do {
                val mapKey = maps.keys.first { it.first == from }
                to = mapKey.second
                mappedSeedVal = maps[mapKey]!!.filter { (from, to) ->
                    from.first <= mappedSeedVal && mappedSeedVal <= from.second
                }.entries.firstOrNull()?.let { (from, to) ->
                    to.first + (mappedSeedVal - from.first)
                } ?: mappedSeedVal
                from = to
            } while (to != "location")

            mappedSeedVal
        }

    }

    fun part2(input: List<String>): Long {
        val (_, seedStr) = input[0].split(':')
        val seedPairs = seedStr.trim().split(' ').map { it.toLong() }.chunked(2)

        var (fromLbl, toLbl) = "" to ""

        val maps = mutableMapOf<Pair<String, String>, MutableMap<Range, Range>>()

        input.subList(2, input.size - 1).forEach { line ->
            if (labelRegex.matches(line)) {
                val matches = labelRegex.find(line)!!
                fromLbl = matches.groupValues[1]
                toLbl = matches.groupValues[2]
                maps.putIfAbsent(fromLbl to toLbl, mutableMapOf())
            } else if (line.trim().isNotEmpty()) {
                val (toStart, fromStart, rangeLength) = line.trim().split(' ').map { it.toLong() }

                maps[fromLbl to toLbl]!![Range.build(fromStart, rangeLength)] = Range.build(toStart, rangeLength)
            }
        }

        //initialize starting conditions for loop
        var inputRanges = seedPairs.map { Range.build(it[0], it[1]) }
        var outputRanges: MutableList<Range>
        fromLbl = "seed"
        toLbl = ""

        do {
            outputRanges = mutableListOf()
            val mapKey = maps.keys.first { it.first == fromLbl }
            toLbl = mapKey.second
            val unmappedInputs = inputRanges.associateWith { listOf(it) }.toMutableMap()

            //  for each translation map range, find intersection(s) with X ranges
            //      for each intersection, do the mapping and subtract the intersection from 'unmapped ranges'
            //  move all leftover unmapped ranges into the mapped ranges (this part is messy - I'm keeping a separate copy
            //      of the input ranges and subtracting out the matches, since modifying the input ranges while iterating
            //      over them could prove difficult. or I'm lazy.)

            maps[mapKey]!!.entries.forEach { (fromRange, toRange) ->
                inputRanges.forEach { inputRange ->
                    inputRange.intersect(fromRange)?.let{ match ->
                        val outputStart = toRange.start + (match.start - fromRange.start)
                        outputRanges.add(Range.build(outputStart, match.len))
                        unmappedInputs[inputRange] = unmappedInputs[inputRange]!!.flatMap { it.subtract(match) }
                    }
                }
            }

            unmappedInputs.values.flatten().forEach { outputRanges.add(it) }

            fromLbl = toLbl
            inputRanges = outputRanges
        } while (toLbl != "location")

        return inputRanges.minOf { it.start }
    }

    val testInput = readTestInput("Day05")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()

    val startTime = LocalDateTime.now()
    part2(input).println()
    val endTime = LocalDateTime.now()
    println(startTime)
    println(endTime)
}

data class Range(val start: Long, val end: Long) {
    val len = end - start + 1
    companion object {
        fun build(start: Long, len: Long): Range {
            return Range(start, start + len - 1)
        }
    }

    fun intersect(other: Range): Range? {
        val newStart = maxOf(start, other.start)
        val newEnd = minOf(end, other.end)

        return if (newStart <= newEnd) {
            Range(newStart, newEnd)
        } else {
            null
        }
    }

    fun subtract(other: Range): List<Range> {
        if (start > other.end || end < other.start) {
            return listOf(this)
        }

        val results = mutableListOf<Range>()

        if (start < other.start) {
            results.add(Range(start, other.start - 1))
        }

        if (end > other.end) {
            results.add(Range(other.end + 1, end))
        }
        return results
    }
}

