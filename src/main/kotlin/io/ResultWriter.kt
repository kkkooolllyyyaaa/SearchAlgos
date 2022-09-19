package io

import model.Adjacency
import model.AlgorithmResult

interface ResultWriter {
    fun write(result: AlgorithmResult)

    fun writeGraph(graph: Map<String, Set<Adjacency>>)
}
