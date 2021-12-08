fun main() {
    fun part1(lines: List<String>): Int {
        var prev = Long.MAX_VALUE
        var counter: Int = 0
        lines.forEach { line ->
            if (line.toLong() > prev)
                counter++

            prev = line.toLong()
        }
        return counter
    }

    fun part2(lines: List<String>): Int {
        var prev = Long.MAX_VALUE
        var counter: Int = 0
        for (i in 3..lines.size) {
            val sum = lines[i-1].toLong() + lines[i-2].toLong() + lines[i-3].toLong()
            if (sum > prev)
                counter++

            prev = sum
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputToLines("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInputToLines("Day01")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day01_2")
    println(part1(input2))
    println(part2(input2))
}
