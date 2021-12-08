import kotlin.math.abs

fun main() {


    fun part1(lines: List<String>): Int {
        val crabPositions = lines[0].trim().split(",").map { it.trim().toInt() }
        return (1..crabPositions.size).map { position ->
            crabPositions.map { abs(it - position) }.sum()
        }.minOrNull() ?: 0
    }

    fun part2(lines: List<String>): Int {
        val crabPositions = lines[0].trim().split(",").map { it.trim().toInt() }
        return (1..crabPositions.size).map { position ->
            crabPositions.map { (1..abs(it - position)).sum() }.sum()
        }.minOrNull() ?: 0
    }


    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))

    val input2 = readInput("Day07_2")
    println(part1(input2))
    println(part2(input2))
}

