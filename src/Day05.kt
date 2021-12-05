import java.lang.Integer.max
import java.lang.Integer.min

fun main() {


    fun part1(lines: List<String>): Int {
        val vectors = lines.map { Vector.parseVector(it) }
        val pointsOverlaps = vectors.filter { it.isHorizontal() || it.isVertical() }.flatMap { it.points() }.groupingBy { it }.eachCount()
        return pointsOverlaps.count { it.value > 1 }
    }

    fun part2(lines: List<String>): Int {

        val vectors = lines.map { Vector.parseVector(it) }
        val pointsOverlaps = vectors.flatMap { it.points() }.groupingBy { it }.eachCount()
        return pointsOverlaps.count { it.value > 1 }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))

    val input2 = readInput("Day05_2")
    println(part1(input2))
    println(part2(input2))
}

data class Vector(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int
) {
    fun isVertical(): Boolean = x1 == x2
    fun isHorizontal(): Boolean = y1 == y2

    fun points(): List<Point> =
        if (isVertical()) {
            (min(y1,y2)..max(y1,y2)).map { y -> Point(x1, y) }.toList()
        } else if (isHorizontal()) {
            (min(x1,x2)..max(x1,x2)).map { x -> Point(x, y1) }.toList()
        } else {
            val minX = min(x1,x2)
            val maxX = max(x1,x2)

            val minY = min(y1,y2)
            val maxY = max(y1,y2)

            check(maxX - minX == maxY - minY)
            val diff = maxX - minX

            (0..diff)
                .map { d -> Point(if(x1==minX) x1+d else x1-d, if (y1 ==minY) y1+d else y1-d) }
                .toList()
        }

    companion object {
        fun parseVector(line: String): Vector =
            line.trim()
                .split("->", ",")
                .map { it.trim().toInt() }
                .let { (x1, y1, x2, y2) -> Vector(x1, y1, x2, y2) }
    }
}

data class Point(val x: Int, val y: Int)