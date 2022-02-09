

fun main() {
    fun part1(lines: List<String>): Int {
        return  0
    }

    fun part2(lines: List<String>): Long {
        return  0
    }

    val testInput = readInputToLines("Day18_test")
    check(part1(testInput) == 4140)
    check(part1(testInput) == 0)

    val input = readInputToLines("Day18")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day18_2")
    println(part1(input2))
    println(part2(input2))
}
