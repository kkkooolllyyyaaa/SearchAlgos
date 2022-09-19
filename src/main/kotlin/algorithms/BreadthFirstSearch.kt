package algorithms

import enum.SearchAlgorithmName
import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

class BreadthFirstSearch : SearchAlgorithm {
    override fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult {
        if (start == destination) {
            return AlgorithmResult(path = listOf(start), summaryDistance = 0)
        }

        val queue = ArrayDeque<String>()
        queue.addLast(start)
        val was = hashSetOf(start)
        val path = mutableMapOf<String, String?>(start to null)

        var current: String
        while (queue.isNotEmpty()) {
            current = queue.removeFirst()
            val adjacentVertices = graph[current]!!
            adjacentVertices.forEach { v ->
                if (v.vertex !in was) {
                    was.add(v.vertex)
                    path[v.vertex] = current
                    queue.addLast(v.vertex)
                }
            }
        }
        return extractPath(destination = destination, path = path)
    }

    override fun algorithmInfo(): AlgorithmInfo {
        return AlgorithmInfo(
            name = SearchAlgorithmName.BREADTH_FIRST_SEARCH,
            timeComplexity = "O(b ^ (d + 1))",
        )
    }
}
