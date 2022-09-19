package algorithms

import enum.SearchAlgorithmName
import flow.AlgorithmFlow
import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

class AStarSearch : SearchAlgorithm {
    override fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult {
        if (start == destination) {
            return AlgorithmResult(path = listOf(start), summaryDistance = 0)
        }

        val heuristics = AlgorithmFlow.HEURISTICS
        if (heuristics.size != graph.size) {
            throw RuntimeException("Size of heuristics is not equal to number of vertices")
        }

        val was = hashSetOf(start)
        val path = mutableMapOf<String, String?>(start to null)

        fun dfs(current: String) {
            was.add(current)
            val vertices = graph[current]!!
            var min = Long.MAX_VALUE
            var vertex: String? = null
            for (el in vertices) {
                if (el.vertex == destination) {
                    vertex = el.vertex
                    break
                }
                val fn = heuristics[el.vertex]!! + el.distance
                if (el.vertex !in was && fn < min) {
                    min = fn
                    vertex = el.vertex
                }
            }

            if (vertex == null)
                exit(start, destination)
            path[vertex!!] = current
            if (vertex == destination)
                return
            dfs(current = vertex)
        }
        dfs(start)
        return extractPath(destination = destination, path = path, graph = graph)
    }

    override fun algorithmInfo(): AlgorithmInfo {
        return AlgorithmInfo(
            name = SearchAlgorithmName.A_STAR,
            timeComplexity = "O(b ^ m)"
        )
    }

    private fun exit(start: String, end: String) {
        throw java.lang.RuntimeException("There is not path from $start to $end")
    }
}
