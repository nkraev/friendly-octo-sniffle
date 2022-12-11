package model

sealed class Command {
    data class Get(val field: String) : Command()
    data class Set(val field: String, val value: String) : Command()

    data class Delete(val field: String) : Command()

    data class Count(val field: String) : Command()
    data class Unknown(val instruction: String) : Command()
}