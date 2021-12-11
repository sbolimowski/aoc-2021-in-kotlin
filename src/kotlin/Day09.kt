
fun main() {

    fun part1(lines: List<String>): Int =
        HeightMap.load(lines).lowPointsRiskLevel()

    fun part2(lines: List<String>): Int =
        HeightMap.load(lines).largestBasins()

    val testInput = readInputToLines("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInputToLines("Day09")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day09_2")
    println(part1(input2))
    println(part2(input2))
}

class HeightMap(
    private val points: List<List<Int>>
) {

    fun lowPointsRiskLevel(): Int = findLowPoints().sumOf { it.riskLevel() }
    fun largestBasins(): Int {
        val lowPoints = findLowPoints()
        return lowPoints
            .map { point -> point.findBasin(this) }
            .map { it.size }
            .sorted()
            .takeLast(3)
            .reduce { acc, i ->  acc * i }
    }

    private fun findLowPoints(): List<HeightMapPoint> {
        return points
            .mapIndexed { y, row ->
                row.mapIndexed { x, value ->
                    if (neighbours(x, y).all { p -> p.value > value }) {
                        HeightMapPoint(x, y, value)
                    } else {
                        null
                    }
                }
            }
            .flatten()
            .filterNotNull()
    }


    private fun point(x: Int, y: Int): HeightMapPoint? =
        if (y in points.indices && x in points[y].indices) {
            HeightMapPoint(x, y, points[y][x])
        } else null

    fun neighbours(x: Int, y: Int): Set<HeightMapPoint> =
        listOf(
            Pair(x + 1, y),
            Pair(x - 1, y),
            Pair(x, y - 1),
            Pair(x, y + 1)
        ).mapNotNull { (nx, ny) -> point(nx, ny) }.toSet()

    companion object {
        fun load(lines: List<String>): HeightMap =
            lines
                .map { line -> line.trim() }
                .filter { it.isNotEmpty() }
                .map { line -> line.map { char -> char.digitToInt() } }
                .let { HeightMap(it) }
    }
}


data class HeightMapPoint(val x: Int, val y: Int, val value: Int) {
    fun riskLevel(): Int = 1 + value

    fun findBasin(heightMap: HeightMap, visited: MutableSet<HeightMapPoint> = mutableSetOf()): Set<HeightMapPoint> {
        if (value == 9 || visited.contains(this)) {
            return emptySet()
        }

        visited.add(this)
        val neighbours = heightMap.neighbours(x, y) - visited
        neighbours
            .forEach {
                it.findBasin(heightMap, visited)
            }

        return visited
    }
}
