package algorithms

import enum.SearchAlgorithmName
import flow.AlgorithmChoice
import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

class IterativeDependingSearch : SearchAlgorithm {
    override fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult {
        if (start == destination) {
            return AlgorithmResult(path = listOf(start), summaryDistance = 0)
        }

        val depthLimitedDfs = AlgorithmChoice().getAlgorithm(SearchAlgorithmName.DEPTH_LIMITED_SEARCH.name)
        DepthLimitedSearch.USE_CALCULATION = false
        DepthLimitedSearch.DEPTH_LIMIT = 1

        var result: AlgorithmResult? = null
        while (DepthLimitedSearch.DEPTH_LIMIT < graph.size) {
            try {
                result = depthLimitedDfs.search(graph = graph, start = start, destination = destination)
                break
            } finally {
                DepthLimitedSearch.DEPTH_LIMIT++
            }
        }
        println("Ended with depthLimit=${DepthLimitedSearch.DEPTH_LIMIT}")
        return result ?: throw RuntimeException("There is no path to $destination")
    }

    override fun algorithmInfo(): AlgorithmInfo {
        return AlgorithmInfo(
            name = SearchAlgorithmName.ITERATIVE_DEPENDING_SEARCH,
            timeComplexity = "O(b ^ d)"
        )
    }
}
