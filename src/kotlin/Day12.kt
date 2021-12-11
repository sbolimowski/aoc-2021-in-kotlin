fun main() {

    fun part1(lines: List<String>): Int = 0

    fun part2(lines: List<String>): Int = 0

    val testInput = readInputToLines("Day12_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInputToLines("Day12")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day12_2")
    println(part1(input2))
    println(part2(input2))
}