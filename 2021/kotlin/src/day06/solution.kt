package day06

import utils.readInput

fun main() {

    fun part1(input: List<String>): Int {
        val fishPool = input[0].split(',').map { it.toInt() }.toMutableList()

        for (i in (1..80)) {
            for (f in fishPool.indices) {
                when(fishPool[f]) {
                    0 -> {
                        fishPool[f] = 6
                        fishPool.add(8)
                    }
                    else -> fishPool[f] -= 1
                }
            }
        }

        return fishPool.size
    }

    fun part2(input: List<String>): Long {
        //the exponential growth is too big for a kotlin list. In part 2 we store `Long` counts of fish that have each
        // timer value - no reason to keep track of individual fish. Sadly, i can't take credit for this - I was stumped
        // and went and got a hint.
        val groupedFishPool: Map<Int, Long> = input[0].split(',').map { it.toInt() }.toMutableList()
            .groupBy { it }
            .mapValues { it.value.size.toLong() }
            .plus(7 to 0)
            .plus(8 to 0)

        return (1..256).fold(groupedFishPool.toDailyFishRecord()) {
            previousDailyRecord, _ -> previousDailyRecord.getNextDayRecord()
        }.countFish()
    }

    val testInput = readInput("day06/test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("day06/input")
    println(part1(input))
    println(part2(input))
}

data class DailyFishRecord(
    val zero: Long = 0,
    val one: Long = 0,
    val two: Long = 0,
    val three: Long = 0,
    val four: Long = 0,
    val five: Long = 0,
    val six: Long = 0,
    val seven: Long = 0,
    val eight: Long = 0
) {
    fun getNextDayRecord(): DailyFishRecord {
        return DailyFishRecord(
            this.one,
            this.two,
            this.three,
            this.four,
            this.five,
            this.six,
            this.zero + this.seven,
            this.eight,
            this.zero
        )
    }

    //...okay, well this method arguably implies that this approach was silly, but it sure feels good
    // from the calling site above!
    fun countFish(): Long {
        return this.zero + this.one + this.two + this.three + this.four + this.five + this.six + this.seven+ this.eight
    }
}

fun Map<Int, Long>.toDailyFishRecord(): DailyFishRecord {
    return DailyFishRecord(
        this.getOrDefault(0, 0),
        this.getOrDefault(1, 0),
        this.getOrDefault(2, 0),
        this.getOrDefault(3, 0),
        this.getOrDefault(4, 0),
        this.getOrDefault(5, 0),
        this.getOrDefault(6, 0),
        this.getOrDefault(7, 0),
        this.getOrDefault(8, 0),
    )
}
