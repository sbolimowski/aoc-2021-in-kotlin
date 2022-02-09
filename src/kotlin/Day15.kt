fun main() {

    fun part1(lines: List<String>): Int {
        val cave = Cave.load(lines)
        return cave.findLowestTotalRisk()
    }

    fun part2(lines: List<String>): Int {
        val cave = Cave.loadBigger(lines)
        return cave.findLowestTotalRisk()
    }


    val testInput = readInputToLines("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)


    val input = readInputToLines("Day15")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day15_2")
    println(part1(input2))
    println(part2(input2))
}

class Cave(private val riskLevels: Map<Point, Int>, private val width: Int, private val height: Int) {

    private val firstPoint = Point(0, 0)
    private val lastPoint = Point(width - 1, height - 1)
    private var counter = 0L

    private val pathsRiskLevels = java.util.PriorityQueue<Pair<Point, Int>> { p1, p2 -> p1.second - p2.second }
    private val d = HashMap<Point, Int>()

    fun adjacent(point: Point): List<Point> =
        listOf(
            Point(point.x - 1, point.y),
            Point(point.x + 1, point.y),
            Point(point.x, point.y - 1),
            Point(point.x, point.y + 1),
        ).filter { it.x in 0 until width && it.y in 0 until height }


    fun setPathRiskLevel(point: Point, value: Int) {
        d[point] = value
        pathsRiskLevels.add(Pair(point, value))
    }

    fun getLowestPathRiskLevel(): Pair<Point, Int> = pathsRiskLevels.poll()

    fun riskLevel(point: Point): Int = riskLevels.getOrDefault(point, Int.MAX_VALUE)

    fun findLowestTotalRisk(): Int {
        setPathRiskLevel(firstPoint, 0)
        val visited = mutableSetOf<Point>()

        while (true) {

            val (point, riskLevel) = getLowestPathRiskLevel()
            if (!visited.contains(point)) {
                if (point == lastPoint) {
                    println("riskLevel: $riskLevel, counter: $counter")
                    return riskLevel
                }
                visited.add(point)

                val adjacent = adjacent(point)
                adjacent.forEach {
                    if (!visited.contains(it)) {


                        val newRiskLevel = riskLevel(it) + riskLevel
                if (d.getOrDefault(it, Int.MAX_VALUE) > newRiskLevel) {
                        setPathRiskLevel(it, newRiskLevel)
                }
                    }
                }

                counter++
            }


        }
    }

    fun print() {
        (0 until height).forEach { y ->
            (0 until width).map { x ->
                val point = Point(x, y)
                val riskLevel = riskLevels[point]
                "$riskLevel"
            }.joinToString("").let { println(it) }
        }
        println()
    }

    companion object {
        fun load(lines: List<String>): Cave {
            val width = lines.first().trim().length
            val height = lines.size

            val riskLevels = lines.mapIndexed { y, line ->
                line.trim().mapIndexed { x, char -> Pair(Point(x, y), char.digitToInt()) }
            }.flatten().associate { it.first to it.second }

            return Cave(riskLevels, width, height)
        }

        fun loadBigger(lines: List<String>): Cave {
            val width = lines.first().trim().length
            val height = lines.size

            val smallRiskLevels = lines.mapIndexed { y, line ->
                line.trim().mapIndexed { x, char -> Pair(Point(x, y), char.digitToInt()) }
            }.flatten().associate { it.first to it.second }

            val riskLevels = mutableMapOf<Point, Int>()

            (0 until width * 5).forEach { x ->
                (0 until height * 5).forEach { y ->
                    val riskLevel = smallRiskLevels.getOrDefault(
                        Point(x % width, y % height),
                        Int.MAX_VALUE
                    ) + x / width + y / height

                    riskLevels[Point(x, y)] = (riskLevel - 1) % 9 + 1

                }
            }

            val cave = Cave(riskLevels, width * 5, height * 5)
            return cave
        }
    }
}
