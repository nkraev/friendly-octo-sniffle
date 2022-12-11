import command.CommandExecutor
import command.CommandParser
import impl.InMemoryMapKeyValueStorage
import model.ExecutionResult

fun main(args: Array<String>) {
    val storage = InMemoryMapKeyValueStorage()

    while (true) {
        val line = readln()
        if (line.lowercase() == "quit") {
            return
        }

        val command = CommandParser.parseCommand(line.trim().split(" "))
        when (val executionResult = CommandExecutor.execute(command, storage)) {
            is ExecutionResult.Success -> executionResult.messageToPrint?.let { println(it) }
            is ExecutionResult.Error -> println(executionResult.errorMessage)
        }
    }
}