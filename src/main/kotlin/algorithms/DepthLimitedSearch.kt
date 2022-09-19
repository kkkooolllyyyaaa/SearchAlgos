package algorithms

import enum.SearchAlgorithmName
import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

class DepthLimitedSearch : SearchAlgorithm {

    companion object {
        var DEPTH_LIMIT = 1
        var USE_CALCULATION = true
    }

    override fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult {
        if (start == destination) {
            return AlgorithmResult(path = listOf(start), summaryDistance = 0)
        }

        val path = mutableMapOf<String, String?>(start to null)
        val depthLimit = if (USE_CALCULATION) analyzeDepth(graph) else DEPTH_LIMIT
        println("Started with depthLimit=$depthLimit")
        val depths = mutableMapOf(start to 0)
        val was = hashSetOf(start)

        fun dfs(current: String) {
            was.add(current)
            val vertices = graph[current]!!
            for (el in vertices) {
                if (el.vertex !in was) {
                    if (depths[current]!! >= depthLimit)
                        continue
                    path[el.vertex] = current
                    depths[el.vertex] = depths[current]!! + 1
                    dfs(el.vertex)
                }
            }
        }
        dfs(start)
        try {
            return extractPath(destination = destination, path = path)
        } catch (e: Exception) {
            throw RuntimeException("${e.message} with depthLimit=$depthLimit")
        }
    }

    override fun algorithmInfo(): AlgorithmInfo {
        return AlgorithmInfo(
            name = SearchAlgorithmName.DEPTH_LIMITED_SEARCH,
            timeComplexity = "b^(dl)",
        )
    }

    private fun analyzeDepth(graph: Map<String, Set<Adjacency>>): Int {
        val n = graph.size
        val m = graph.map { it.value.size }.sum()

        return if (m < n) {
            m * 2 / 3
        } else {
            n / 3
        }
    }
}
