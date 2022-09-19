import flow.AlgorithmChoice
import flow.AlgorithmFlow
import io.*

fun main(args: Array<String>) {
    val graphReader: GraphReader = CsvGraphReader()
    val algorithmChoice = AlgorithmChoice()
    val inputReader: InputReader = ConsoleInputReader()
    val resultWriter: ResultWriter = ConsoleResultWriter()

    val flow = AlgorithmFlow(
        graphReader = graphReader,
        algorithmChoice = algorithmChoice,
        inputReader = inputReader,
        resultWriter = resultWriter,
    )

    flow.startFlow("/graph.csv")
}