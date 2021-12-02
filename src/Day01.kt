fun main() {
    fun part1(lines: List<String>): Int {
        var horizontal = 0
        var depth = 0
        lines.forEach { line ->
            val (operation, argument) = line.trim().split(" ")
            when (operation) {
                "forward" -> {
                    horizontal += argument.toInt()
                }
                "down" -> {
                    depth += argument.toInt()
                }
                "up" ->
                    depth -= argument.toInt()
            }
        }
        return horizontal * depth
    }

    fun part2(lines: List<String>): Long {
        var horizontal: Long = 0
        var depth: Long = 0
        var aim: Long = 0
        lines.forEach { line ->
            val (operation, argument) = line.trim().split(" ")
            when (operation) {
                "forward" -> {
                    horizontal += argument.toLong()
                    depth += aim * argument.toLong()
                }
                "down" -> {
                    aim += argument.toLong()
                }
                "up" ->
                    aim -= argument.toLong()
            }
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900L)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

    val input2 = readInput("Day02_2")
    println(part1(input2))
    println(part2(input2))
}
