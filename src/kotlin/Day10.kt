
fun main() {

    fun scoreFirstError(line: String): Int {
        val openBrackets = mutableListOf<Char>()

        line.forEach { char ->
            when (char) {
                '(', '[', '{', '<' -> openBrackets.add(char)

                ')' -> if (openBrackets.last() == '(') openBrackets.removeLast() else return 3
                ']' -> if (openBrackets.last() == '[') openBrackets.removeLast() else return 57
                '}' -> if (openBrackets.last() == '{') openBrackets.removeLast() else return 1197
                '>' -> if (openBrackets.last() == '<') openBrackets.removeLast() else return 25137
            }
        }

        return 0
    }


    fun scoreClosing(line: String): Long {
        val openBrackets = mutableListOf<Char>()

        line.trim().forEach { char ->
            when (char) {
                '(', '[', '{', '<' -> openBrackets.add(char)

                ')' -> if (openBrackets.last() == '(') openBrackets.removeLast() else return -1
                ']' -> if (openBrackets.last() == '[') openBrackets.removeLast() else return -1
                '}' -> if (openBrackets.last() == '{') openBrackets.removeLast() else return -1
                '>' -> if (openBrackets.last() == '<') openBrackets.removeLast() else return -1
            }
        }
        var score = 0L

        fun bracketValue(bracket: Char): Long =
            when(bracket) {
                '(', ')' -> 1
                '[', ']' -> 2
                '{', '}' -> 3
                '<', '>' -> 4
                else -> 0
            }

        openBrackets.reversed().forEach { bracket ->
            score = score * 5 + bracketValue(bracket)
        }

        return score
    }


    fun part1(lines: List<String>): Int = lines.sumOf { line -> scoreFirstError(line) }

    fun part2(lines: List<String>): Long {
        val sorted = lines.map { scoreClosing(it) }.filter { it >= 0 }.sorted()
        return sorted[sorted.size/2]
    }

    val testInput = readInputToLines("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInputToLines("Day10")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day10_2")
    println(part1(input2))
    println(part2(input2))
}