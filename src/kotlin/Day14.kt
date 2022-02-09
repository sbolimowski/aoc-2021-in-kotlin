fun main() {

    fun part1(lines: List<String>): Int {
        val polymerTemplate = lines.first().trim()

        val insertionRules = lines
            .map { it.trim().split(" -> ") }
            .filter { it.size > 1 }
            .associate { it.first() to it[1].first() }

        var polymer = polymerTemplate.toMutableList()
        (1..10).forEach { step ->
            val tmp = polymer.toMutableList()
            var j = 0
            (1..polymer.size-1).forEach { i ->
                val key = polymer.subList(i-1, i+1).joinToString("")
                insertionRules[key]?.let {
                    tmp.add(i + j++, it)
                }
            }
            polymer = tmp
        }

        val counts = polymer.groupingBy { it }.eachCount()
        val max = counts.maxOf { it.value }
        val min = counts.minOf { it.value }

        return max - min
    }

    fun part2(lines: List<String>): Long {
        val polymerTemplate = lines.first().trim()
        val firstChar = polymerTemplate.first()

        val insertionRules = lines
            .map { it.trim().split(" -> ") }
            .filter { it.size > 1 }
            .associate { it.first() to it[1].first() }

        val pairsCounts = polymerTemplate
            .windowed(2) { it.toString() }
            .groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()

        (1..40).forEach { step ->
            val tmp = pairsCounts.toMap()
            pairsCounts.clear()
            tmp.forEach { pair, count ->
                val char = insertionRules.get(pair)!!

                val newPair1 = "${pair.first()}${char}"
                val newPair2 = "${char}${pair.last()}"

                pairsCounts[newPair1] = pairsCounts.getOrDefault(newPair1, 0) + count
                pairsCounts[newPair2] = pairsCounts.getOrDefault(newPair2, 0) + count
            }
        }
        val counts = pairsCounts
            .keys.map { Pair(it.last(), pairsCounts[it]) }
            .groupBy { it.first }
            .mapValues { it.value.sumOf { e -> e.second ?: 0 } }
            .toMutableMap()

        counts[firstChar] = counts.getOrDefault(firstChar, 0) + 1L
        val max = counts.maxOf { it.value }
        val min = counts.minOf { it.value }
        return max - min
    }


    val testInput = readInputToLines("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529L)



    val input = readInputToLines("Day14")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day14_2")
    println(part1(input2))
    println(part2(input2))
}
