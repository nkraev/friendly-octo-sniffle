import command.CommandParser
import model.Command
import model.Result
import model.TestParseResult

class TestParser {
    /** parses test that is represented as a list of strings from the test file **/
    fun parseTest(test: List<String>): List<TestParseResult> {
        var currentlyProcessing = 0
        val results = mutableListOf<TestParseResult>()
        while (currentlyProcessing < test.size) {
            val line = test[currentlyProcessing]
            val command = CommandParser.parseCommand(line.trim().substring(2).split(" "))
            val hasOutput = currentlyProcessing + 1 < test.size && !test[currentlyProcessing + 1].isCommand()
            val result = if (hasOutput) parseResult(test[currentlyProcessing + 1]) else null
            val parseResult = when (result) {
                null -> TestParseResult.CommandOnly(command)
                else -> TestParseResult.CommandAndResult(command, result)
            }
            currentlyProcessing += if (result == null) 1 else 2
            results.add(parseResult)
        }

        return results
    }

    private fun parseResult(line: String): Result = Result(line.trim())

    private fun String.isCommand() = startsWith("> ")
}