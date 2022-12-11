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
        testsToParse.forEach { executeTest(it) }
    }

    private fun executeTest(test: File) {
        val inputStream = FileInputStream(test)
        val lines = inputStream.bufferedReader().readLines()
        val parsed = testParser.parseTest(lines)
        println(">> Test: $parsed")
    }
}