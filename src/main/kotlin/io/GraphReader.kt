package io

import model.Adjacency

interface GraphReader {
    fun readGraph(filepath: String): Map<String, Set<Adjacency>>

    fun readHeuristics(): Map<String, Long>
}
