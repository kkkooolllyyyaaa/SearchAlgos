package io

import enum.SearchAlgorithmName
import java.util.*

class ConsoleInputReader : InputReader {
    private val scanner = Scanner(System.`in`)

    override fun readAlgorithm(): String {
        println("List of algorithms:")
        SearchAlgorithmName.values().forEachIndexed { index, it ->
            println("$index - ${it.name}")
        }
        endLine()
        println("Input algorithm:")

        val algorithm = readLine()
        endLine()
        return (if (isNumber(algorithm)) extractAlgorithm(algorithm) else algorithm)
    }

    override fun readStartAndDestination(vertices: List<String>): Pair<String, String> {
        println("List of cities:")
        vertices.forEachIndexed { index, s -> println("$index - $s") }
        endLine()

        val start = readStart()
        val destination = readDestination()
        endLine()

        return (if (isNumber(start)) extractCity(vertices, start) else start) to
                (if (isNumber(destination)) extractCity(vertices, destination) else destination)
    }

    private fun readStart(): String {
        println("Input start city name:")
        return readLine()
    }

    private fun readDestination(): String {
        println("Input destination city name:")
        return readLine()
    }

    private fun readLine() = scanner.nextLine()

    private fun isNumber(potentialNumber: String) =
        kotlin.runCatching { potentialNumber.toLong() }.isSuccess

    private fun extractCity(vertices: List<String>, index: String): String {
        val result = kotlin.runCatching {
            return@runCatching vertices[index.toInt()]
        }
        if (result.isSuccess) {
            return result.getOrNull()!!
        } else {
            throw RuntimeException("There is no city with number=$index")
        }
    }

    private fun extractAlgorithm(index: String): String {
        val result = kotlin.runCatching {
            return@runCatching SearchAlgorithmName.values()[index.toInt()].name
        }
        if (result.isSuccess) {
            return result.getOrNull()!!
        } else {
            throw RuntimeException("There is no city with number=$index")
        }
    }

    private fun endLine(){
        println()
    }
}
