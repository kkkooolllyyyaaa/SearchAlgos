package algorithms

import enum.SearchAlgorithmName
import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

class DepthFirstSearch : SearchAlgorithm {
    override fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult {
        if (start == destination) {
            return AlgorithmResult(path = listOf(start), summaryDistance = 0)
        }

        val was = hashSetOf(start)
        val path = mutableMapOf<String, String?>(start to null)

        fun dfs(current: String) {
            was.add(current)
            val vertices = graph[current]!!
            for (el in vertices) {
                if (el.vertex !in was) {
                    path[el.vertex] = current
                    dfs(el.vertex)
                }
            }
        }
        dfs(start)
        return extractPath(destination = destination, path = path)
    }

    override fun algorithmInfo(): AlgorithmInfo {
        return AlgorithmInfo(
            name = SearchAlgorithmName.DEPTH_FIRST_SEARCH,
            timeComplexity = "b ^ m",
        )
    }
}
