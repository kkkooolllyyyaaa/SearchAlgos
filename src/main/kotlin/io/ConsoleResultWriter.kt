package io

import model.Adjacency
import model.AlgorithmResult

class ConsoleResultWriter : ResultWriter {
    override fun write(result: AlgorithmResult) {
        val summary = result.summaryDistance
        val path = result.path.joinToString(" - ") { it }

        println("Summary distance: $summary")
        println("Path: $path")
    }

    override fun writeGraph(graph: Map<String, Set<Adjacency>>) {
        graph.toSortedMap().forEach { (t, u) ->
            val vertices = u.joinToString(", ") { it.vertex }
            println("$t: $vertices")
        }
        println()
    }
}
