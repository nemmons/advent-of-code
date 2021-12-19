package day13

import utils.readInput
import utils.toInts
import java.util.*


fun parseInstructions(lines: List<String>): Pair<Set<Pair<Int,Int>>, List<String>> {
    val gap = lines.indexOf("")
    val points = lines.subList(0,gap).map {
        it.split(",").let { coords -> Pair(coords[0].toInt(), coords[1].toInt() )}
    }.toSet()
    val folds = lines.subList(gap+1, lines.size)
    return Pair(points, folds)
}

fun foldUp(points: Set<Pair<Int,Int>>, foldY: Int) : Set<Pair<Int,Int>> {
    val folded = mutableSetOf<Pair<Int,Int>>()

    points.forEach { (x,y) ->
        if (y > foldY) {
            val newY = foldY - (y - foldY)
            folded.add(Pair(x,newY))
        } else {
            folded.add(Pair(x,y))
        }
    }
    return folded
}

fun foldLeft(points: Set<Pair<Int,Int>>, foldX: Int) : Set<Pair<Int,Int>> {
    val folded = mutableSetOf<Pair<Int,Int>>()

    points.forEach { (x,y) ->
        if (x > foldX) {
            val newX = foldX - (x - foldX)
            folded.add(Pair(newX,y))
        } else {
            folded.add(Pair(x,y))
        }
    }
    return folded
}


fun main() {
    fun part1(lines: List<String>): Int {
        val (points, folds) = parseInstructions(lines)

        val instruction = folds[0].split(" ")[2].split("=")
        val pos = instruction[1].toInt()
        val result = when (instruction[0]) {
            "x" -> foldLeft(points, pos)
            "y" -> foldUp(points, pos)
            else -> throw Exception("What?")
        }
        return result.size
    }


    fun part2(lines: List<String>) {
        var (points, folds) = parseInstructions(lines)

        folds.forEach {
            val instruction = it.split(" ")[2].split("=")
            val pos = instruction[1].toInt()
            points = when (instruction[0]) {
                "x" -> foldLeft(points, pos)
                "y" -> foldUp(points, pos)
                else -> throw Exception("What?")
            }
        }

        val maxX = points.maxOf { it.first }
        val maxY = points.maxOf { it.second }

        (0..maxY).forEach { y ->
            (0..maxX).map { x ->
                if (points.contains(Pair(x,y))) {
                    '#'
                } else {
                    ' '
                }
            }.joinToString("").let { println(it) }
        }


    }

    val testInput = readInput("day13/test")

    check(part1(testInput) == 17)

    val input = readInput("day13/input")
    println(part1(input))
    part2(input)
}

