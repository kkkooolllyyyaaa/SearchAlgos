package algorithms

import enum.SearchAlgorithmName
import model.Adjacency
import model.AlgorithmInfo
import model.AlgorithmResult

class BidirectionalSearch : SearchAlgorithm {
    override fun search(graph: Map<String, Set<Adjacency>>, start: String, destination: String): AlgorithmResult {
        if (start == destination) {
            return AlgorithmResult(path = listOf(start), summaryDistance = 0)
        }

        val wasStart = hashSetOf(start)
        val qStart = ArrayDeque<String>()
        qStart.addLast(start)
        val pathStart = mutableMapOf<String, String?>(start to null)

        val wasEnd = hashSetOf(destination)
        val qEnd = ArrayDeque<String>()
        qEnd.addLast(destination)
        val pathEnd = mutableMapOf<String, String?>(destination to null)

        var common: String? = null
        while (qStart.isNotEmpty() && qEnd.isNotEmpty()) {
            val countStart = qStart.size
            for (i in 0 until countStart) {
                val current = qStart.removeFirst()
                val adjacentVertices = graph[current]!!
                adjacentVertices.forEach { v ->
                    if (v.vertex !in wasStart) {
                        wasStart.add(v.vertex)
                        pathStart[v.vertex] = current
                        qStart.addLast(v.vertex)
                    }
                }
            }

            val countEnd = qEnd.size
            for (i in 0 until countEnd) {
                val current = qEnd.removeFirst()
                val adjacentVertices = graph[current]!!
                adjacentVertices.forEach { v ->
                    if (v.vertex !in wasEnd) {
                        wasEnd.add(v.vertex)
                        pathEnd[v.vertex] = current
                        qEnd.addLast(v.vertex)
                    }
                }
            }

            val intersect = wasStart intersect wasEnd
            if (intersect.isNotEmpty()) {
                common = intersect.first()
                val startResult = extractPath(
                    destination = common,
                    path = pathStart
                )

                val endResult = extractPath(
                    destination = common,
                    path = pathEnd
                )

                return AlgorithmResult(
                    summaryDistance = startResult.summaryDistance + endResult.summaryDistance,
                    path = startResult.path + endResult.path.reversed().drop(1)
                )
            }
        }
        throw RuntimeException("There is no path from $start to $destination")
    }

    override fun algorithmInfo(): AlgorithmInfo {
        return AlgorithmInfo(
            name = SearchAlgorithmName.BIDIRECTIONAL_SEARCH,
            timeComplexity = "O(b ^ (d / 2))"
        )
    }
}
