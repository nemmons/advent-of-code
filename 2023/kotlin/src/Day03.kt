
typealias RowNumber = Int
typealias ColumNumber = Int
typealias PartNumber = Int

fun main() {
    val numberRegex= """\d+""".toRegex()
    val symbolRegex = """[^\d.\n]""".toRegex()


    fun findPartNumbers(input: List<String>): MutableMap<RowNumber, MutableMap<ColumNumber, PartNumber>> {
        val partNumbers = mutableMapOf<RowNumber, MutableMap<ColumNumber, PartNumber>>()
        input.forEachIndexed { i, line ->
            numberRegex.findAll(line).forEach {
                partNumbers.putIfAbsent(i, mutableMapOf())
                partNumbers[i]!![it.range.first] = it.value.toInt()
            }
        }
        return partNumbers
    }

    fun findSymbolPositions(input: List<String>): MutableMap<RowNumber, MutableList<Int>> {
        val symbolPositions = mutableMapOf<RowNumber, MutableList<ColumNumber>>()
        input.forEachIndexed{ i, line ->
            symbolRegex.findAll(line).forEach {
                symbolPositions.putIfAbsent(i, mutableListOf())
                symbolPositions[i]?.add(it.range.first)
            }
        }
        return symbolPositions
    }

    fun part1(input: List<String>): Int {
        val numbers = findPartNumbers(input)
        val symbols = findSymbolPositions(input)

        var sum = 0

        numbers.entries.forEach { (numY, numMap) ->
            numMap.entries.forEach { (numX, num) ->
                val numLen = num.toString().length

                //top
                if (numY > 0) {
                    symbols[numY - 1]?.any { symX ->
                        (numX + numLen >= symX && numX <= symX +1)
                    }.run {
                        if (this == true) {
                            sum += num
                        }
                    }
                }

                //bottom
                if (numY < input.size) {
                    symbols[numY + 1]?.any { symX ->
                        (numX + numLen >= symX && numX <= symX +1)
                    }.run {
                        if (this == true) {
                            sum += num
                        }
                    }
                }

                //left/right
                symbols[numY]?.any { symX ->
                    numX + numLen == symX || numX == symX + 1
                }.run {
                    if (this == true) {
                        sum += num
                    }
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val numbers = findPartNumbers(input)
        val symbols = findSymbolPositions(input)

        var gearRatioSums = 0

        symbols.forEach { (symY, lineSymbols) ->
            lineSymbols.forEach { symX ->
                var adjacentNums = 0
                var runningProduct = 1

                //top
                if (symY > 0) {
                    numbers[symY - 1]?.entries?.forEach { (numX, num) ->
                        val numLen = num.toString().length
                        if  (numX + numLen >= symX && numX <= symX +1) {
                            adjacentNums++
                            runningProduct *= num
                        }
                    }
                }

                //bottom
                if (symY < input.size) {
                    numbers[symY + 1]?.entries?.forEach { (numX, num) ->
                        val numLen = num.toString().length
                        if  (numX + numLen >= symX && numX <= symX +1) {
                            adjacentNums++
                            runningProduct *= num
                        }
                    }
                }

                //left /right
                numbers[symY]?.entries?.forEach { (numX, num) ->
                    val numLen = num.toString().length
                    if (numX + numLen == symX || numX == symX + 1) {
                        adjacentNums++
                        runningProduct *= num
                    }
                }

                if (adjacentNums == 2) {
                    gearRatioSums += runningProduct
                    println("Found a gear at $symY,$symX : ${input[symY][symX]}")

                }
            }
        }

        return gearRatioSums
    }

    val testInput = readTestInput("Day03")
    //check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    //part1(input).println()
    part2(input).println()
}

