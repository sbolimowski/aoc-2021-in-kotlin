fun main() {

    fun foldNum(n: Int, fold: Int): Int =
        if (n < fold){
            n
        } else {
            2 * fold - n
        }

    fun part1(lines: List<String>): Int {
        val points = lines
            .map { it.trim() }
            .filter { it.isNotBlank() && it.first().isDigit() }
            .map { line ->
                val (x, y) = line.split(",")
                Point(x.toInt(), y.toInt())
            }

        val fold = lines
                .find { it.trim().startsWith("fold") }
                ?.let { it.trim().split(" ")[2].split("=") }
            ?: emptyList()

        val (foldType, foldValue) = fold

        val foldedPoints = when (foldType) {
            "x" -> points.map { Point(foldNum(it.x, foldValue.toInt()) , it.y) }
            "y" -> points.map { Point(it.x, foldNum(it.y, foldValue.toInt())) }
            else -> emptyList()
        }.toSet()


        return foldedPoints.size
    }



    fun part2(lines: List<String>): Int {
        val points = lines
            .map { it.trim() }
            .filter { it.isNotBlank() && it.first().isDigit() }
            .map { line ->
                val (x, y) = line.split(",")
                Point(x.toInt(), y.toInt())
            }

        var foldedPoints = points.toSet()
        lines
            .filter { it.trim().startsWith("fold") }
            .map { it.trim().split(" ")[2].split("=") }
            .forEach { fold ->
                val (foldType, foldValue) = fold

                foldedPoints = when (foldType) {
                    "x" -> foldedPoints.map { Point(foldNum(it.x, foldValue.toInt()) , it.y) }
                    "y" -> foldedPoints.map { Point(it.x, foldNum(it.y, foldValue.toInt())) }
                    else -> emptyList()
                }.toSet()
            }

        val max_x = foldedPoints.maxOf { it.x }
        val max_y = foldedPoints.maxOf { it.y }

        (0..max_y).forEach { y ->
            (0..max_x).forEach { x ->
                if (foldedPoints.contains(Point(x,y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            print("\n")

        }

        return 0
    }


    val testInput = readInputToLines("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == 0)


    val input = readInputToLines("Day13")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day13_2")
    println(part1(input2))
    println(part2(input2))
}