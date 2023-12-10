import java.time.LocalDateTime


fun main() {
    val lblRegex = "[0-9A-Z]+".toRegex()

    fun part1(input: List<String>): Int {
        val nodes = mutableMapOf<String, Node>()
        val directions = input[0]
        input.subList(2, input.lastIndex + 1).forEach {
            val (lbl, lft, rght) = lblRegex.findAll(it).toList().map { match -> match.value }
            nodes[lbl] = Node(lbl, lft, rght)
        }

        var steps = 0
        var pos = "AAA"
        val dirsLen = directions.length

        while(true) {
            val dir = directions[steps.mod(dirsLen)]
            val node = nodes[pos]
            pos = when(dir) {
                'R' -> node!!.rght
                'L' -> node!!.lft
                else -> throw Exception("Invalid Direction $dir")
            }
            steps += 1
            if (pos == "ZZZ") {
                return steps
            }
        }

        throw Exception ("Could't find ZZZ!")
    }

    fun part2(input: List<String>): Int {
        val nodes = mutableMapOf<String, Node>()
        val directions = input[0]
        input.subList(2, input.lastIndex + 1).forEach {
            val (lbl, lft, rght) = lblRegex.findAll(it).toList().map { match -> match.value }
            nodes[lbl] = Node(lbl, lft, rght)
        }

        var steps = 0
        var positions = nodes.keys.filter { it.last() == 'A' }.toMutableList()
        val dirsLen = directions.length

        while(true) {
            val dir = directions[steps.mod(dirsLen)]

            positions.indices.forEach { index ->
                val node = nodes[positions[index]]
                positions[index] = when(dir) {
                    'R' -> node!!.rght
                    'L' -> node!!.lft
                    else -> throw Exception("Invalid Direction $dir")
                }
            }
            steps += 1
            if (positions.all { it.last() == 'Z' }) {
                return steps
            }
        }

        throw Exception ("Could't find ZZZ!")
    }


    val testInput = readTestInput("Day08")
    check(part1(testInput) == 2)

    val testInput2 = readTestInput("Day08_2")
    check(part1(testInput2) == 6)

    val input = readInput("Day08")
    part1(input).println()

    val testInput3 = readTestInput("Day08_3")
    check(part2(testInput3) == 6)


    val startTime = LocalDateTime.now()
    part2(input).println()
    val endTime = LocalDateTime.now()
    println(startTime)
    println(endTime)
}

data class Node(val label: String, val lft: String, val rght: String)
