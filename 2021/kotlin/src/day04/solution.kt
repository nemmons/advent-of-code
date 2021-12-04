package day04

import utils.readInput

fun main() {
    fun loadBoards(input: List<String>): MutableList<BingoBoard> {
        val boards: MutableList<BingoBoard> = mutableListOf()

        //assume a square bingo board
        val boardSize = input[2].split(" ").filter { it != "" }.size

        var i = 2

        while (i < input.size) {
            boards.add(input.subList(i, i+boardSize).toBingoBoard())
            i += boardSize + 1 //account for the blank line gap
        }
        return boards
    }

    fun part1(input: List<String>): Int {
        val draws: List<Int> = input[0].split(",").map { it.toInt() }
        val boards: MutableList<BingoBoard> = loadBoards(input)

        for (draw in draws) {
            boards.forEach {
                it.checkAndFill(draw)
                if (it.evaluateBoard()) {
                    val score = it.calcScore()
                    return draw * score
                }
            }
        }
        throw Exception("No winner found. Sad!")
    }

    fun part2(input: List<String>): Int {
        val draws: List<Int> = input[0].split(",").map { it.toInt() }
        val boards: MutableList<BingoBoard> = loadBoards(input)
        val winners = mutableListOf<Pair<Int, Int>>() //pair board index to winning draw num. clunky, i know

        for (draw in draws) {
            boards.forEachIndexed { i, board ->
                if (winners.map { it.first }.contains(i)) { //ugh this is expensive, probably fix this
                    return@forEachIndexed //this board already won, we don't care
                }
                board.checkAndFill(draw)
                if (board.evaluateBoard()) {
                    winners += Pair(i, draw)
                }
            }
        }
        val lastWinner = boards[winners.last().first]
        return lastWinner.calcScore() * winners.last().second
    }

    val testInput = readInput("day04/test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("day04/input")
    println(part1(input))
    println(part2(input))
}

data class BingoSpace(
    val num: Int,
    var filled: Boolean
)

data class BingoBoard(
    var spaces: List<List<BingoSpace>>
) {
    fun checkAndFill(target: Int) {
        for (line in spaces) {
            for ((i, space) in line.withIndex()) {
                if (target == space.num) {
                    line[i].filled = true
                }
            }
        }
    }

    fun evaluateBoard(): Boolean {
        val filledRow = this.spaces.any { line ->
            !line.any { space -> !space.filled }
        }
        if (filledRow) {
            return true
        }

        val filledCol = this.spaces[0].indices.any { x ->
            !this.spaces.indices.any { y ->
                !spaces[y][x].filled
            }
        }
        if (filledCol) {
            return true
        }
        return false
    }

    //TODO: can probably clean this up with a smarter map->reduuce
    //TODO: better idea: calculate a 'potential score' when creating board, based on sum of all spaces,
    // then subtract from that whenever we fill a space.
    fun calcScore(): Int {
        var score = 0
        for (y in spaces.indices) {
            for (x in spaces[y].indices) {
                if (!spaces[y][x].filled) { score += spaces[y][x].num }
            }
        }
        return score
    }
}

fun List<String>.toBingoBoard(): BingoBoard {
    val spaces = this.map { line ->
        line.split(" ").filter { it != "" }.map { char -> BingoSpace(char.trim().toInt(), false) }
    }
    return BingoBoard(spaces)
}

