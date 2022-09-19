package io

interface InputReader {
    fun readAlgorithm(): String

    fun readStartAndDestination(vertices: List<String>): Pair<String, String>
}
