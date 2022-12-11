package model

data class Result(val expected: String)

sealed class TestParseResult {
    data class CommandOnly(val command: Command) : TestParseResult()
    data class CommandAndResult(val command: Command, val result: Result) : TestParseResult()
}

data class TestExecutionError(val testParseResult: TestParseResult, val executionResult: ExecutionResult) :
    Error("Error when executing test, testParseResult: $testParseResult; executionResult: $executionResult")