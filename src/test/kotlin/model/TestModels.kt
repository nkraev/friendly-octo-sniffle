package model

sealed class Command {
    data class Get(val field: String) : Command()
    data class Set(val field: String, val value: String) : Command()
    data class Unknown(val instruction: String) : Command()
}

data class Result(val expected: String)

sealed class TestParseResult {
    data class CommandOnly(val command: Command) : TestParseResult()
    data class CommandAndResult(val command: Command, val result: Result) : TestParseResult()
}