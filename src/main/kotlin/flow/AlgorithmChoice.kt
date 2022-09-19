package flow

import algorithms.*
import enum.SearchAlgorithmName

class AlgorithmChoice {
    fun getAlgorithm(name: String): SearchAlgorithm {
        val algorithmName = SearchAlgorithmName.values().find { name == it.name }
            ?: throw RuntimeException("There is no such algorithm with name=$name")

        return when (algorithmName) {
            SearchAlgorithmName.BREADTH_FIRST_SEARCH -> BreadthFirstSearch()
            SearchAlgorithmName.DEPTH_FIRST_SEARCH -> DepthFirstSearch()
            SearchAlgorithmName.DEPTH_LIMITED_SEARCH -> DepthLimitedSearch()
            SearchAlgorithmName.ITERATIVE_DEPENDING_SEARCH -> IterativeDependingSearch()
            SearchAlgorithmName.BIDIRECTIONAL_SEARCH -> BidirectionalSearch()
            SearchAlgorithmName.GREEDY_FIRST_BEST -> GreedyFirstBestSearch()
            SearchAlgorithmName.A_STAR -> AStarSearch()
        }
    }
}
