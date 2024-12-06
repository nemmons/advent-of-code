import java.time.LocalDateTime


fun main() {

    fun parseMap(input: List<String>): TileMap {
        val tiles = mutableMapOf<YPos, MutableMap<XPos, Tile>>()
        var startPos: Position = -1 to -1

        input.forEachIndexed { y, line ->
            tiles.putIfAbsent(y, mutableMapOf())
            line.trim().forEachIndexed { x, char ->
                val exitDirs = mutableListOf<Dir>()
                when(char) {
                    '|' -> {
                        exitDirs.add(Dir.N)
                        exitDirs.add(Dir.S)
                    }
                    '-' -> {
                        exitDirs.add(Dir.E)
                        exitDirs.add(Dir.W)
                    }
                    'L' -> {
                        exitDirs.add(Dir.N)
                        exitDirs.add(Dir.E)
                    }
                    'J' -> {
                        exitDirs.add(Dir.N)
                        exitDirs.add(Dir.W)
                    }
                    '7' -> {
                        exitDirs.add(Dir.W)
                        exitDirs.add(Dir.S)
                    }
                    'F' -> {
                        exitDirs.add(Dir.S)
                        exitDirs.add(Dir.E)
                    }
                    '.' -> {
                        //No-op
                    }
                    'S' -> {
                        startPos = y to x
                    }
                    else -> throw Exception("Unexpected character $char found in input")
                }
                val exits = if (exitDirs.isNotEmpty()) {
                    exitDirs.associateWith { dir ->
                        when(dir) {
                            Dir.N -> (y-1) to x
                            Dir.E -> y to (x + 1)
                            Dir.S -> (y + 1) to x
                            Dir.W -> y to (x - 1)
                        }
                    }
                } else mapOf()

                tiles[y]!![x] = Tile(y to x, exits)
            }
        }

        val (startY, startX) = startPos
        val startPosNeighbors = listOf(
            (startY - 1) to startX,
            (startY + 1) to startX,
            startY to (startX - 1),
            startY to (startX + 1),
        )
        val startPosExits = startPosNeighbors.mapNotNull { (neighborY, neighborX) ->
            tiles[neighborY]?.get(neighborX)?.let { tile ->
                tile.exits.entries.firstOrNull { it.value == startPos }?.let { exit ->
                    exit.key.opposite() to tile.pos
                }
            }
        }.associate { it.first to it.second }
        tiles[startPos.first]!![startPos.second] = Tile(startPos, startPosExits, true)

        if (startPos == -1 to -1) {
            throw Exception("Unable to find true start pos)")
        }

        return TileMap(tiles, startPos)
    }

    fun getPath(tileMap: TileMap): List<Tile> {
        var pos = tileMap.startPos

        var tile = tileMap.tiles[pos.first]!![pos.second]!!
        val firstStep = tileMap.tiles[pos.first]!![pos.second]!!.exits.entries.first()
        var fromDir = firstStep.key.opposite()
        var path = mutableListOf<Tile>()

        do {
            var nextStep = tile.exits.entries.first { it.key != fromDir }
            tile = tileMap.tiles[nextStep.value.first]!![nextStep.value.second]!!
            fromDir = nextStep.key.opposite()
            path.add(tile)
        } while(!tile.isStart)

        return path
    }

    fun part1(input: List<String>): Int {
        val tilemap = parseMap(input)
        val path = getPath(tilemap)

        return path.size / 2
    }

    fun part2(input: List<String>): Long {
        val tilemap = parseMap(input)
        val path = getPath(tilemap)

        val pathTiles = path.groupBy { it.pos.first }.toList().map{ (_, tiles) ->
            tiles.associateBy { it.pos.second }
        }

        var spaceInside: Long = 0L

        pathTiles.forEach{ rowMap ->
            println(rowMap)

            println(rowMap.keys.sorted().chunked(2))
            println(rowMap.keys.sorted().chunked(2).filter { it.size == 2}.sumOf { it.last() - it.first() - 1 })
            println()
\
            val diff = rowMap.keys.sorted().chunked(2).filter { it.size == 2}.
                sumOf { it.last() - it.first() - 1 }

            println("Adding $diff to $spaceInside ")
            spaceInside += diff

            println("resulted in $spaceInside")
        }
        return spaceInside
    }


    val testInput = readTestInput("Day10")
    check(part1(testInput) == 4)

    val testInput2 = readTestInput("Day10_2")
    check(part1(testInput2) == 8)


    val input = readInput("Day10")
    part1(input).println()

    val testInput3 = readTestInput("Day10_3")
    val testInput4 = readTestInput("Day10_4")

    check(part2(testInput3) == 4L)
    check(part2(testInput4) == 10L)


    val startTime = LocalDateTime.now()
    part2(input).println()
    val endTime = LocalDateTime.now()
    println(startTime)
    println(endTime)
}


enum class Dir(){
    N,
    S,
    E,
    W
}
fun Dir.opposite() = when(this) {
    Dir.N -> Dir.S
    Dir.S -> Dir.N
    Dir.E -> Dir.W
    Dir.W -> Dir.E
}

typealias XPos = Int
typealias YPos = Int
typealias Position = Pair<YPos, XPos>

data class Tile(
    val pos: Position,
    val exits: Map<Dir, Position>,
    val isStart: Boolean = false
)


data class TileMap(
    val tiles: MutableMap<YPos, MutableMap<XPos, Tile>>,
    val startPos: Position
)
