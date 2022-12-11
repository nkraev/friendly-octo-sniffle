import command.CommandExecutor
import impl.InMemoryMapKeyValueStorage
import model.ExecutionResult
import model.TestParseResult
import java.io.File
import java.io.FileInputStream
import kotlin.test.Test

class InMemoryMapKeyValueTestExecutor {
    private val testParser = TestParser()
    private val storage = InMemoryMapKeyValueStorage()

    @Test
    fun readAndExecuteTests() {
        val file = File("src/test/resources")
        val testsToParse = file.listFiles()
            ?.filter { it.name.startsWith("Test_") }
            ?: emptyList()

        println(">> Tests to execute: ${testsToParse.joinToString()}")
        testsToParse.forEach { executeTest(it) }
    }

    private fun executeTest(test: File) {
        println(">> Running test: ${test.name}...")
        val inputStream = FileInputStream(test)
        val lines = inputStream.bufferedReader().readLines()
        val parsed = testParser.parseTest(lines)
        try {
            parsed.forEach { it.assertExpected() }
        } catch (e: AssertionError) {
            println("❌ Test failed")
            return
        }

        println("✅ Test completed successfully!")
    }

    private fun TestParseResult.assertExpected() = when (this) {
        is TestParseResult.CommandOnly -> {
            val execution = CommandExecutor.execute(command, storage)
            assert(execution is ExecutionResult.Success)
        }

        is TestParseResult.CommandAndResult -> {
            val expectedResultMessage =
                when (val executionResult = CommandExecutor.execute(command, storage)) {
                    is ExecutionResult.Success -> executionResult.messageToPrint ?: ""
                    is ExecutionResult.Error -> executionResult.errorMessage
                }
            assert(expectedResultMessage == result.expected)
        }
    }
}