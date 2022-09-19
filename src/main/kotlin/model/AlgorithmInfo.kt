package model

import enum.SearchAlgorithmName

data class AlgorithmInfo(
    val name: SearchAlgorithmName,
    val timeComplexity: String,
)
