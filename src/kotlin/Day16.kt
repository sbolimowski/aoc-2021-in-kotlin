interface Packet {
    val version: Int
    val typeId: Int
    val length: Int
    fun versionSum(): Int
    fun calculateValue(): Long
    fun print(): Unit

    companion object {
        fun calculateValue(typeId: Int, values: List<Long>): Long =
            when (typeId) {
                0 -> values.sum()
                1 -> values.reduce { a, b -> a * b }
                2 -> values.minOf { it }
                3 -> values.maxOf { it }

                5 -> if (values[0] > values[1]) 1 else 0
                6 -> if (values[0] < values[1]) 1 else 0
                7 -> if (values[0] == values[1]) 1 else 0
                else -> 0
            }
    }
}

data class LiteralPacket(
    override val version: Int,
    override val typeId: Int,
    override val length: Int,
    val value: Long
) : Packet {
    override fun versionSum(): Int = version
    override fun calculateValue(): Long = value
    override fun print() {
        println("Ver: $version, type: $typeId, value: $value")
    }
}

data class OperatorPacket(
    override val version: Int,
    override val typeId: Int,
    val lengthTypeId: Char,
    override val length: Int,
    val subPackets: List<Packet>
) : Packet {
    override fun versionSum(): Int = version + subPackets.sumOf { it.versionSum() }
    override fun calculateValue(): Long = Packet.calculateValue(typeId, subPackets.map { it.calculateValue() })
    override fun print() {
        println("********************************************")
        println("Ver: $version, type: $typeId")
        subPackets.forEach { it.print() }
        println("********************************************")
    }
}

fun parse(bits: String): Packet? {
    if (bits.length < 6) return null
    val packetVersion = bits.substring(0, 3).toInt(2)
    val packetTypeId = bits.substring(3, 6).toInt(2)

//    println("bits: $bits -> packet type: $packetTypeId, version: $packetVersion")

    when (packetTypeId) {
        4 -> {
            val values = mutableListOf<String>()
            val valuesBits = bits.substring(6).windowed(5, 5)
            var i = 6
            valuesBits.forEach { value ->
                values.add(value.substring(1))
                i += 5
                if (value.startsWith("0")) {
                    return LiteralPacket(packetVersion, packetTypeId, i, values.joinToString("").toLong(2))
                }
            }
        }
        else -> {
            val lengthTypeId = bits[6]
            if (lengthTypeId == '0') {
                val numberOfBitsInSubPackets = bits.substring(7, 22).toInt(2)
                val subPackets = mutableListOf<Packet>()
                var i = 22
                do {
                    val subPacket = if (i < 22 + numberOfBitsInSubPackets) parse(
                        bits.substring(
                            i,
                            22 + numberOfBitsInSubPackets
                        )
                    ) else null
                    if (subPacket != null) {
                        subPackets.add(subPacket)
                        i += subPacket.length
                    }
                } while (subPacket != null)

                return OperatorPacket(
                    packetVersion,
                    packetTypeId,
                    lengthTypeId,
                    22 + numberOfBitsInSubPackets,
                    subPackets
                )

            } else {
                val numberOfSubPackets = bits.substring(7, 18).toInt(2)
                val subPackets = mutableListOf<Packet>()
                var i = 18
                (1..numberOfSubPackets).forEach {
                    val subPacket = parse(bits.substring(i, bits.length))
                    if (subPacket != null) {
                        subPackets.add(subPacket)
                        i += subPacket.length
                    }
                }
                return OperatorPacket(packetVersion, packetTypeId, lengthTypeId, i, subPackets)
            }
        }
    }

    return null
}

fun main() {
    fun part1(line: String): Int {
        val bits = line.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
        return parse(bits)?.versionSum() ?: 0
    }

    fun part2(line: String): Long {
        val bits = line.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
        val packet = parse(bits)
    //    packet?.print()
        return packet?.calculateValue() ?: 0
    }

    part1("D2FE28")
    part1("38006F45291200")
    part1("EE00D40C823060")
    val testInput = readInputToLine("Day16_test")
    check(part1(testInput) == 16)
    val testInput2 = readInputToLine("Day16_test2")
    check(part1(testInput2) == 12)
    val testInput3 = readInputToLine("Day16_test3")
    check(part1(testInput3) == 23)
    val testInput4 = readInputToLine("Day16_test4")
    check(part1(testInput4) == 31)


    check(part2("C200B40A82") == 3L)
    check(part2("04005AC33890") == 54L)
    check(part2("880086C3E88112") == 7L)
    check(part2("CE00C43D881120") == 9L)
    check(part2("D8005AC2A8F0") == 1L)
    check(part2("F600BC2D8F") == 0L)
    check(part2("9C005AC2F8F0") == 0L)
    check(part2("9C0141080250320F1802104A08") == 1L)

    val input = readInputToLine("Day16")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLine("Day16_2")
    println(part1(input2))
    println(part2(input2))
}
