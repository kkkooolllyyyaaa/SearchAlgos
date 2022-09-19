package flow

import io.GraphReader
import io.InputReader
import io.ResultWriter
import model.Adjacency
import mu.KLogging
import mu.withLoggingContext

class AlgorithmFlow(
    private val graphReader: GraphReader,
    private val algorithmChoice: AlgorithmChoice,
    private val inputReader: InputReader,
    private val resultWriter: ResultWriter,
) {

    companion object : KLogging() {
        var HEURISTICS: Map<String, Long> = emptyMap()
        val NOT_READ_SET = setOf(
            "GREEDY_FIRST_BEST",
            "A_STAR"
        )

        val FIRST_VAR_DESTINATION = "Одесса"
        val FIRST_VAR_START = "Мурманск"
    }

    fun startFlow(filePath: String) {
        try {
            val graph = graphReader.readGraph(filepath = filePath)
            println("Got graph:")
            resultWriter.writeGraph(graph)

            val vertices = extractVertices(graph)
            HEURISTICS = graphReader.readHeuristics()
            val algorithmName = inputReader.readAlgorithm()
            val (start, destination) = if (algorithmName in NOT_READ_SET) {
                FIRST_VAR_START to FIRST_VAR_DESTINATION
            } else {
                inputReader.readStartAndDestination(vertices = vertices)
            }

            val contextInfo = mapOf(
                "algorithmName" to algorithmName,
                "start" to start,
                "endVertex" to destination
            )

            withLoggingContext(contextInfo) {
                logger.info { "Started flow with input: $contextInfo" }
                val algorithm = algorithmChoice.getAlgorithm(algorithmName)
                logger.info { "Got algorithm: ${algorithm.algorithmInfo()}" }

                val result = algorithm.search(
                    graph = graph,
                    start = start,
                    destination = destination
                )
                logger.info { "Got result: $result" }

                resultWriter.write(result)
                logger.info { "Result is successfully wrote" }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun extractVertices(graph: Map<String, Set<Adjacency>>): List<String> =
        mutableSetOf<String>().apply {
            graph.forEach { this.add(it.key) }
        }.toList().sorted()
}
