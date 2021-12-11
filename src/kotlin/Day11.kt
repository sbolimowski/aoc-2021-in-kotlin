
fun main() {

    fun part1(lines: List<String>): Int {
        val octopuses = Octopuses.load(lines)
        (1..100).forEach {
            octopuses.step()
        }
        val countFlashes = octopuses.countFlashes()
        return countFlashes
    }

    fun part2(lines: List<String>): Int {
        val octopuses = Octopuses.load(lines)
        var prevCountFlashes = octopuses.countFlashes()
        var step = 0
       do {
           step++
            octopuses.step()
           val countFlashes = octopuses.countFlashes()
           val diff = countFlashes - prevCountFlashes
           prevCountFlashes = countFlashes

        } while (diff < 100 && step < 10000)
        return step
    }

    val testInput = readInputToLines("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInputToLines("Day11")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day11_2")
    println(part1(input2))
    println(part2(input2))
}

class Octopuses(val data: List<List<Octopus>>) {

    fun step() {

        val flashed = mutableListOf<Octopus>()
        data.forEach { row ->
            row.forEach { octopus ->
                if (octopus.inc()) {
                    flashed.add(octopus)
                }

            }
        }


        do {
            val tmp = flashed.toList()
            flashed.clear()
            tmp.forEach {
                val neighbours = neighbours(it.x, it.y)
                neighbours.forEach { n ->
                    if (n.flashInc()) {
                        flashed.add(n)
                    }
                }
            }
        } while (flashed.isNotEmpty())
    }

    fun print() {
        data.forEach { row ->
            println(row.map { it.value }.joinToString(""))
        }
    }

    fun countFlashes(): Int =
        data.flatMap { row -> row.map { it.flashCount } }.sum()

    private fun octopus(x: Int, y: Int): Octopus? =
        if (y in data.indices && x in data[y].indices) {
            data[y][x]
        } else null

    private fun neighbours(x: Int, y: Int): Set<Octopus> =
        listOf(
            Pair(x + 1, y),
            Pair(x + 1, y + 1),
            Pair(x + 1, y - 1),
            Pair(x - 1, y),
            Pair(x - 1, y + 1),
            Pair(x - 1, y - 1),
            Pair(x, y - 1),
            Pair(x, y + 1)
        ).mapNotNull { (nx, ny) -> octopus(nx, ny) }.toSet()

    companion object {
        fun load(lines: List<String>): Octopuses =
            lines
                .map { line -> line.trim() }
                .filter { it.isNotEmpty() }
                .mapIndexed { y,line -> line.map { char -> char.digitToInt() } .mapIndexed { x,v -> Octopus(x,y, v) } }
                .let { Octopuses(it) }
    }
}

class Octopus(val x: Int, val y: Int, var value: Int, var flashCount: Int = 0) {
    fun inc(): Boolean {
        value++
        if (value > 9) {
            flashCount++
            value = 0
            return true
        }
        return false
    }
    fun flashInc(): Boolean {
        if (value > 0) {
            value++
        }
        if (value > 9) {
            flashCount++
            value = 0
            return true
        }
        return false
    }
}