fun main() {

    fun part1(input: List<String>): Long {
        val (_, seedStr) = input[0].split(':')
        val seeds = seedStr.trim().split(' ').map { it.toLong() }

        val labelRegex = "([a-z]+)-to-([a-z]+) map:".toRegex()

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

        val labelRegex = "([a-z]+)-to-([a-z]+) map:".toRegex()

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

                maps[fromLbl to toLbl]!![fromStart to fromStart + rangeLength - 1] = toStart to toStart + rangeLength - 1
            }
        }

        return seedPairs.minOf { (seedStart, seedCount) ->
            println(seedStart to seedCount)
            var from = "seed"
            var to: String

            var nextMapInputs = mutableListOf<LongRange>(
                (seedStart until seedStart + seedCount)
            )
            do {
                val mapInputs = nextMapInputs
                nextMapInputs = mutableListOf()
                val mapKey = maps.keys.first { it.first == from }
                to = mapKey.second

                val mapsFrom = maps[mapKey]!!

                mapInputs.forEach { inputRange ->
                    mapsFrom.forEach { (from, to) ->
                        val (fromStart, fromStop) = from
                        val overlap = inputRange.intersect(fromStart..fromStop)
                        if (overlap.isNotEmpty()) {
                            val firstTo = to.first + (overlap.first() - inputRange.first)
                            nextMapInputs.add(firstTo until firstTo+overlap.size)
                        }

                    }
                }

                mapInputs.

                from = to
            } while (to != "location")
            nextMapInputs.minOf { it.first }
        }
    }

    val testInput = readTestInput("Day05")
    //check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    //part1(input).println()
    part2(input).println()
}

