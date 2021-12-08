fun main() {

    fun part1(lines: List<String>): Int {


        val tmp = lines
            .map { it.map { c -> c.toString().toInt() } }
            .reduce { acc: List<Int>, bytes -> acc.zip(bytes) { a, b -> a + b } }
            .map { if (it > lines.size / 2) 1 else 0 }

        val gamma = tmp
            .joinToString(separator = "")
            .toInt(2)
        //println(gamma.toString(2))

        val epsilon = tmp
            .map { if (it == 1) 0 else 1 }
            .joinToString(separator = "")
            .toInt(2)
        //println(epsilon.toString(2))

        return gamma * epsilon
    }

    fun part2(lines: List<String>): Int {
        val values = lines.map { it.map { c -> c.toString().toInt() } }

        var o2 = values
        var co2 = values

        for (i in 0..12) {

            if (o2.size > 1) {
                val mostCommon = if (o2.count { it[i] == 1 } >= o2.count { it[i] == 0 }) 1 else 0
                o2 = o2.filter { it[i] == mostCommon }
            }


            if (co2.size > 1) {
                val leastCommon = if (co2.count { it[i] == 1 } >= co2.count { it[i] == 0 }) 0 else 1
                co2 = co2.filter { it[i] == leastCommon }
            }

            if (o2.size == 1 && co2.size == 1) break
        }

       val o2rating = o2.first().joinToString(separator = "").toInt(2)
       val co2rating = co2.first().joinToString(separator = "").toInt(2)

        return o2rating * co2rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputToLines("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInputToLines("Day03")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day03_2")
    println(part1(input2))
    println(part2(input2))
}
