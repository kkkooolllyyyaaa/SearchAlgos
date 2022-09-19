package io

import model.Adjacency
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Paths

class CsvGraphReader : GraphReader {
    override fun readGraph(filepath: String): Map<String, Set<Adjacency>> {
        val resource = javaClass.getResource(filepath) ?: throw RuntimeException("Invalid filename")
        val bufferedReader = BufferedReader(FileReader(Paths.get(resource.toURI()).toFile()))
        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT)

        val entries = csvParser.map {
            it.get(0).trim() to Adjacency(
                vertex = it.get(1).trim(),
                distance = it.get(2).trim().toLong(),
            )
        }

        val graph = hashMapOf<String, HashSet<Adjacency>>()
        entries.forEach {
            graph[it.first]?.add(it.second) ?: run {
                graph[it.first] = hashSetOf(it.second)
            }

            val reverseAdjacency = Adjacency(vertex = it.first, distance = it.second.distance)
            graph[it.second.vertex]?.add(reverseAdjacency) ?: run {
                graph[it.second.vertex] = hashSetOf(reverseAdjacency)
            }
        }
        return graph
    }

    override fun readHeuristics(): Map<String, Long> {
        val resource = javaClass.getResource("/heuristicOdessa.csv") ?: throw RuntimeException("Invalid filename")
        val bufferedReader = BufferedReader(FileReader(Paths.get(resource.toURI()).toFile()))
        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT)

        val entries = mapOf(*(csvParser.map {
            it.get(0).trim() to it.get(1).trim().toLong()
        }.toTypedArray()))
        return entries
    }
}
