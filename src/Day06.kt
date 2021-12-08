fun main() {


    fun part1(lines: List<String>): Int {
        val fishes: MutableMap<Int, Int> = lines[0].trim().split(",").map { it.trim().toInt() }.groupingBy { it }.eachCount().toMutableMap()

        for (i in 1..80) {
            val fishesCopy: Map<Int, Int> = fishes.toMap()
            for (j in 1..8) {
                fishes[j-1] = fishesCopy.getOrDefault(j, 0)
            }
            fishes[8] = fishesCopy.getOrDefault(0, 0)
            fishes[6] = fishesCopy.getOrDefault(0, 0) + fishesCopy.getOrDefault(7, 0)
        }
        return fishes.values.sum()
    }

    fun part2(lines: List<String>): Long {

        val fishes: MutableMap<Int, Long> = lines[0].trim().split(",").map { it.trim().toInt() }.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

        for (i in 1..256) {
            val fishesCopy: Map<Int, Long> = fishes.toMap()
            for (j in 1..8) {
                fishes[j-1] = fishesCopy.getOrDefault(j, 0)
            }
            fishes[8] = fishesCopy.getOrDefault(0, 0)
            fishes[6] = fishesCopy.getOrDefault(0, 0) + fishesCopy.getOrDefault(7, 0)
        }
        return fishes.values.sum()
    }


    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))

    val input2 = readInput("Day06_2")
    println(part1(input2))
    println(part2(input2))
}

