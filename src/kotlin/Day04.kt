fun main() {

    fun loadBoard(lines: List<String>): Board {
        check(lines.size == 5)
        val numbers = lines
            .map { line -> line.trim().split("\\s+".toRegex()).map { it.trim().toInt() } }
        return Board.fromNumbers(numbers)
    }

    fun loadWinningNumbers(lines: List<String>) =
        lines[0].trim().split(",").map { it.toInt() }

    fun loadBoards(lines: List<String>): List<Board> {
        val boards = mutableListOf<Board>()
        for (i in 1 until lines.size step 6) {
            boards.add(loadBoard(lines.subList(i + 1, i + 6)))
        }
        return boards
    }

    fun part1(lines: List<String>): Int {
        val winningNumbers = loadWinningNumbers(lines)
        val boards = loadBoards(lines)

        winningNumbers.forEach { n ->
            boards.forEach { board -> board.mark(n) }
            val winningBoard = boards.find { it.checkWin() }
            if (winningBoard != null) {
                return winningBoard.calculateResult(n)
            }
        }
        return 0
    }

    fun part2(lines: List<String>): Int {
        val winningNumbers = loadWinningNumbers(lines)
        val boards = loadBoards(lines)

        winningNumbers.forEach { n ->
            val lastToWin = boards.filter { !it.checkWin() }
            boards.forEach { board -> board.mark(n) }
            if (lastToWin.size == 1) {
                if (lastToWin.first().checkWin()) {
                    return lastToWin.first().calculateResult(n)
                }
            }
        }
        return 0
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputToLines("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInputToLines("Day04")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day04_2")
    println(part1(input2))
    println(part2(input2))
}

class Board(
    private val data: Array<Array<BoardValue>>
) {
    fun mark(value: Int) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (data[i][j].value == value) {
                    data[i][j] = BoardValue(value, true)
                }
            }
        }
    }

    fun checkWin(): Boolean {
        for (i in 0..4) {
            if (checkCol(i)) return true
        }

        for (i in 0..4) {
            if (checkRow(i)) return true
        }

        return false
    }

    fun calculateResult(number: Int): Int =
        data.sumOf { col ->
            col.filter { !it.marked }
                .sumOf { it.value }
        } * number


    private fun checkCol(colNum: Int): Boolean =
        data[colNum].all { it.marked }

    private fun checkRow(rowNum: Int): Boolean {
        for (i in 0..4) {
            if (!data[i][rowNum].marked) {
                return false
            }
        }
        return true
    }

    companion object {
        fun fromNumbers(numbers: List<List<Int>>): Board {
            check(numbers.size == 5)
            for (i in 0..4) {
                check(numbers[i].size == 5)
            }
            val data = Array(5) { i ->
                Array(5) { j ->
                    BoardValue(numbers[i][j], false)
                }
            }

            return Board(data)
        }
    }
}

data class BoardValue(val value: Int, val marked: Boolean)