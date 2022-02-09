fun main() {

    fun part1(lines: List<String>): Int {
        val graph = Graph()
        lines.forEach { line -> graph.parseLine(line) }

        return graph.findPathsCount()
    }

    fun part2(lines: List<String>): Int {
        val graph = Graph()
        lines.forEach { line -> graph.parseLine(line) }

        return graph.findPathsCount2()
    }


    val testInput = readInputToLines("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val testInput2 = readInputToLines("Day12_test2")
    check(part1(testInput2) == 19)
    check(part2(testInput2) == 103)

    val testInput3 = readInputToLines("Day12_test3")
    check(part1(testInput3) == 226)
    check(part2(testInput3) == 3509)

    val input = readInputToLines("Day12")
    println(part1(input))
    println(part2(input))

    val input2 = readInputToLines("Day12_2")
    println(part1(input2))
    println(part2(input2))
}

class Graph(
    private val matrix: MutableMap<GraphNode, MutableMap<GraphNode, Boolean>> = mutableMapOf(),
    var counter: Int = 0
) {

    fun parseLine(line: String) {
        val (nodeName1, nodeName2) = line.trim().split("-")
        val node1 = GraphNode.fromName(nodeName1)
        val node2 = GraphNode.fromName(nodeName2)

        addNode(node1)
        addNode(node2)

        if (node2.type != GraphNodeType.START && node1.type != GraphNodeType.END) {
            addConnection(node1, node2)
        }
        if (node1.type != GraphNodeType.START && node2.type != GraphNodeType.END) {
            addConnection(node2, node1)
        }
    }

    fun findPathsCount(): Int = findPaths(GraphNode(GraphNodeType.START, "start")).size
    fun findPathsCount2(): Int = findPaths2(GraphNode(GraphNodeType.START, "start")).size

    private fun findPaths(node: GraphNode, visited: Set<GraphNode> = emptySet()): Set<List<GraphNode>> {
        if (counter++ > 1_000_000) return emptySet()
        val paths = mutableSetOf<List<GraphNode>>()
        val connections = matrix.get(node)
        connections?.forEach { (child, hasConnection) ->
            if (hasConnection && !(child.type == GraphNodeType.SMALL && visited.contains(child))) {
                val childPaths = findPaths(child, visited + node)
                childPaths.forEach { childPath ->
                    paths.add(listOf(node) + childPath)
                }
            }
        }
        if (node.type == GraphNodeType.END) {
            paths.add(listOf(node))
        }

        return paths
    }

    private fun findPaths2(
        node: GraphNode,
        visited: Set<GraphNode> = emptySet(),
        secondVisited: GraphNode? = null
    ): Set<List<GraphNode>> {
        if (counter++ > 1000_000) return emptySet()
        val paths = mutableSetOf<List<GraphNode>>()
        val connections = matrix.get(node)
        connections?.forEach { (child, hasConnection) ->
            if (hasConnection && !(child.type == GraphNodeType.SMALL && visited.contains(child) && secondVisited != null)) {
                val newSecondVisited = if (child.type == GraphNodeType.SMALL && visited.contains(child) && secondVisited == null) child else secondVisited
                val childPaths = findPaths2(child, visited + node, newSecondVisited)
                childPaths.forEach { childPath ->
                    paths.add(listOf(node) + childPath)
                }
            }
        }
        if (node.type == GraphNodeType.END) {
            paths.add(listOf(node))
        }

        return paths
    }

    fun printPath(path: List<GraphNode>) {
        println(path.map { it.name }.joinToString(","))
    }


    fun print() {
        val nodes = matrix.keys.toList()
        nodes.forEachIndexed { i, node ->
            if (i == 0) {
                println("*".padEnd(8) + nodes.map { it.printName() }.joinToString(""))
            }
            val row = nodes.map { matrix[node]?.get(it) }.joinToString(",\t")
            println("${node.printName()} ${row}")
        }
    }

    private fun addNode(node: GraphNode) {
        if (!matrix.containsKey(node)) {
            matrix.put(node, mutableMapOf())

            val keys = matrix.values.first().keys
            keys.forEach { key ->
                matrix[node]?.put(key, false)
            }
            (keys + node).forEach { key ->
                matrix[key]?.put(node, false)
            }
        }
    }

    private fun addConnection(formNode: GraphNode, toNode: GraphNode) {
        matrix.get(formNode)?.put(toNode, true)
    }
}

enum class GraphNodeType {
    START,
    END,
    BIG,
    SMALL
}

data class GraphNode(val type: GraphNodeType, val name: String) {
    fun printName(): String = (name + ",").padEnd(8)

    companion object {
        fun fromName(name: String): GraphNode =
            when {
                name == "start" -> GraphNode(GraphNodeType.START, "start")
                name == "end" -> GraphNode(GraphNodeType.END, "end")
                name.first().isUpperCase() -> GraphNode(GraphNodeType.BIG, name)
                else -> GraphNode(GraphNodeType.SMALL, name)
            }
    }
}


/*
-       start   A   b   c   d   end
start   0       1   1   0   0   0
A       0       0   1   1   0   1
b       0       1   0   0   1   1
c       0       1   0   0   0   0
d       0       0   1   0   0   0
end     0       0   0   0   0   0


 */