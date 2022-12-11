import command.CommandExecutor
import impl.InMemoryMapKeyValueStorage
import model.ExecutionResult
import model.KeyValueStorageInterface
import model.TestExecutionError
import model.TestParseResult
import java.io.File
import java.io.FileInputStream
import kotlin.test.Test

class InMemoryMapKeyValueTestExecutor {
    private val testParser = TestParser()

    @Test
    fun readAndExecuteTests() {
        val file = File("src/test/resources")
        val testsToParse = file.listFiles()
            ?.filter { it.name.startsWith("Test_") }
            ?: emptyList()

        println(">> Tests to execute: ${testsToParse.joinToString()}")
        testsToParse.forEach { executeTest(InMemoryMapKeyValueStorage(), it) }
    }

    private fun executeTest(storage: KeyValueStorageInterface, test: File) {
        println(">> Running test: ${test.name}...")
        val inputStream = FileInputStream(test)
        val lines = inputStream.bufferedReader().readLines()
        val parsed = testParser.parseTest(lines)
        try {
            parsed.forEach { it.assertExpected(storage) }
        } catch (e: TestExecutionError) {
            println("❌ Test failed")
            return
        }

        println("✅ Test completed successfully!")
    }

    private fun TestParseResult.assertExpected(storage: KeyValueStorageInterface) {
        when (this) {
            is TestParseResult.CommandOnly -> {
                val execution = CommandExecutor.execute(command, storage)
                if (execution !is ExecutionResult.Success) {
                    throw TestExecutionError(this, execution)
                }
            }

            is TestParseResult.CommandAndResult -> {
                val executionResult = CommandExecutor.execute(command, storage)
                val expectedResultMessage =
                    when (executionResult) {
                        is ExecutionResult.Success -> executionResult.messageToPrint ?: ""
                        is ExecutionResult.Error -> executionResult.errorMessage
                    }
                if (expectedResultMessage != result.expected) {
                    throw TestExecutionError(this, executionResult)
                }
            }
        }
    }
}