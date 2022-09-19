package algorithms

import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

interface SearchAlgorithm {
    fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult

    fun algorithmInfo(): AlgorithmInfo

    fun extractPath(destination: String, path: Map<String, String?>): AlgorithmResult {
        val pathList = mutableListOf(destination)
        var prev: String? = path[destination] ?: throw RuntimeException("There is no path to $destination")

        while (prev != null) {
            pathList.add(prev)
            prev = path[prev]
        }

        return AlgorithmResult(
            summaryDistance = (pathList.size - 1).toLong(),
            path = pathList.reversed()
        )
    }

    fun extractPath(
        destination: String,
        path: Map<String, String?>,
        graph: Map<String, Set<Adjacency>>,
    ): AlgorithmResult {
        val pathList = mutableListOf(destination)
        var prev: String? = path[destination] ?: throw RuntimeException("There is no path to $destination")
        var summaryDistance = 0L
        while (prev != null) {
            pathList.add(prev)
            val next = path[prev]
            if (next != null) {
                summaryDistance += graph[next]!!.first { it.vertex == prev }.distance
            }
            prev = next
        }

        return AlgorithmResult(
            summaryDistance = summaryDistance,
            path = pathList.reversed()
        )
    }
}
