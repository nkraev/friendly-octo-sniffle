package model

data class Result(val expected: String)

sealed class TestParseResult {
    data class CommandOnly(val command: Command) : TestParseResult()
    data class CommandAndResult(val command: Command, val result: Result) : TestParseResult()
}