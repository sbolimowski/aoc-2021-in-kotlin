fun main() {

    fun part1(lines: List<String>): Int =
        lines.flatMap { it.split("|")[1].trim().split(" ", "  ") }.count { it.length in setOf(2, 4, 3, 7) }


    fun sortedString(it: String) = it.trim().toCharArray().apply { sort() }.joinToString("")

    fun toDigitsList(str: String) =
        str.trim().split(" ", "  ").map { s -> sortedString(s) }


    fun <T> permutations(list: List<T>): Set<List<T>> {
        if (list.isEmpty()) return setOf(emptyList())

        val result: MutableSet<List<T>> = mutableSetOf()
        for (i in list.indices) {
            val tmpList = list - list[i]
            val permutations = permutations(tmpList)
            permutations.forEach { item ->
                result.add(item + list[i])
            }
        }
        return result
    }


    fun charsMappings(chars: List<Char>): List<Map<Char, Char>> =
        permutations(chars).map { permutation ->
            permutation.mapIndexed { index, char -> chars[index] to char }.toMap()
        }

    fun strMapping(charMapping: Map<Char, Char>, strPattern: String): String =
        charMapping.filterKeys { it in strPattern.toCharArray() }.values.sorted().joinToString("")

    fun parseDigits(
        digits: List<String>,
        digitsMapping: Map<String, String>
    ): Int = digits.map { digitsMapping.getOrDefault(it, "-1") }.joinToString("").toInt()

    fun part2(lines: List<String>): Int {
        val parsedLines = lines
            .map { line -> line.split("|") }
            .map { Pair(toDigitsList(it[0]), toDigitsList(it[1])) }

        val chars = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
        val charsMappings = charsMappings(chars)


        val digitsMappings = charsMappings.map { charMapping ->
            mapOf(
                strMapping(charMapping, "abcefg") to "0",
                strMapping(charMapping, "cf") to "1",
                strMapping(charMapping, "acdeg") to "2",
                strMapping(charMapping, "acdfg") to "3",
                strMapping(charMapping, "bcdf") to "4",
                strMapping(charMapping, "abdfg") to "5",
                strMapping(charMapping, "abdefg") to "6",
                strMapping(charMapping, "acf") to "7",
                strMapping(charMapping, "abcdefg") to "8",
                strMapping(charMapping, "abcdfg") to "9",
            )
        }

        return parsedLines.map { p ->
            val signal = p.first
            val value = p.second
            val matchingDigitMappings = digitsMappings.filter { digitMapping ->
                digitMapping.keys.containsAll(signal + value)
            }

            check(matchingDigitMappings.size == 1)
            val parsedValue = parseDigits(value, matchingDigitMappings.first())
            parsedValue
        }.sum()
    }


    val testInput = readInputToLines("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInputToLines("Day08")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day08_2")
    println(part1(input2))
    println(part2(input2))
}

