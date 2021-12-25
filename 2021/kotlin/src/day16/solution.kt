package day16

import utils.readInput
import java.lang.Long.parseLong


val literalRegex = "^([01]{3})(100)(1([01]){4}+)*(0([01]){4}+)".toRegex()

private val hexBinMapping = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111"
)

data class Packet(
    val version: Int,
    val type: Int,
    val literalVal: Long? = null,
    val subpackets: List<Packet> = listOf()
) {
    fun versionTotal(): Int = version + subpackets.sumOf { it.versionTotal() }

    fun evaluate(): Long {

        return when(type) {
            0 -> subpackets.sumOf { it.evaluate() }
            1 -> subpackets.fold(1L) { acc, n ->
                acc * n.evaluate()
            }
            2 -> subpackets.minOf { it.evaluate() }
            3 -> subpackets.maxOf { it.evaluate() }
            4 -> literalVal ?: throw Exception("Missing Literal Value!")
            5 -> if (subpackets[0].evaluate() > subpackets[1].evaluate()) { 1 } else { 0 }
            6 -> if (subpackets[0].evaluate() < subpackets[1].evaluate()) { 1 } else { 0 }
            7 -> if (subpackets[0].evaluate() == subpackets[1].evaluate()) { 1 } else { 0 }
            else -> throw Exception("Unknown type $type")
        }
    }
}

fun main() {
    fun hex2bin(hex: String) = hex.map { hexBinMapping[it] }.joinToString("")

    //TODO clean up this monstrosity
    fun parsePacket(packetBits: String): Pair<Packet, String> {
        val packetVersion = Integer.parseInt(packetBits.substring(0,3),2)
        val packetType = Integer.parseInt(packetBits.substring(3,6),2)

        if (packetType == 4) {
            val thisPacketBits = literalRegex.find(packetBits)!!.value

            val bitGroups = (thisPacketBits.length - 6) / 5
            val binStr = (0 until bitGroups).joinToString("") { n ->
                thisPacketBits.substring(6 + (n * 5) + 1, 6 + (n * 5) + 5)
            }
            val literal = parseLong(binStr, 2)
            return Pair(Packet(packetVersion, packetType, literal), packetBits.drop(thisPacketBits.length))
        } else {
            val lengthTypeId = packetBits[6]
            if (lengthTypeId == '0') {
                val bitLength = Integer.parseInt(packetBits.substring(7, 22), 2)
                var remainingSubpacketBits = packetBits.substring(22)
                val subPackets = mutableListOf<Packet>()
                val targetRemainderLen = packetBits.length - (bitLength + 22)
                while (remainingSubpacketBits.length > targetRemainderLen) { //NOPE
                    val (subpacket, remainder) = parsePacket(remainingSubpacketBits)
                    subPackets.add(subpacket)
                    remainingSubpacketBits = remainder
                }
                return Pair(Packet(packetVersion, packetType, subpackets = subPackets),remainingSubpacketBits)
            } else if (lengthTypeId == '1') {
                val subpacketNum = Integer.parseInt(packetBits.substring(7,18), 2)
                var remainingSubpacketBits = packetBits.substring(18)
                val subPackets = (0 until subpacketNum).map{
                    val (subpacket, remainder) = parsePacket(remainingSubpacketBits) //regex is probably too greedy when there are two subpackets there
                    remainingSubpacketBits = remainder
                    subpacket
                }
                return Pair(Packet(packetVersion, packetType, subpackets = subPackets), remainingSubpacketBits)
            }
        }
        throw Exception("Didn't returN!")
    }

    fun part1(lines: List<String>): Int {
        val (packet, _) = parsePacket(hex2bin((lines[0])))

        return packet.versionTotal()
    }


    fun part2(input: String): Long {
        val (packet, _) = parsePacket(hex2bin((input)))

        val thing =  packet.evaluate()
        return thing
    }

    val testInput = readInput("day16/test")
    val test2Input = readInput("day16/test2")
    val test3Input = readInput("day16/test3")
    val test4Input = readInput("day16/test4")

    check(part1(testInput) == 16)
    check(part1(test2Input) == 12)
    check(part1(test3Input) == 23)
    check(part1(test4Input) == 31)

    check(part2("C200B40A82") == 3L)
    check(part2("04005AC33890") == 54L)
    check(part2("880086C3E88112") == 7L)
    check(part2("CE00C43D881120") == 9L)
    check(part2("D8005AC2A8F0") == 1L)
    check(part2("F600BC2D8F") == 0L)
    check(part2("9C005AC2F8F0") == 0L)
    check(part2("9C0141080250320F1802104A08") == 1L)
    check(part2("3232D42BF9400") == 5000000000L)
    check(part2("26008C8E2DA0191C5B400") == 10000000000L)
    check(part2("8A004A801A8002F478") == 15L)

    val input = readInput("day16/input")
    println(part1(input))
    println(part2(input[0]))
}
