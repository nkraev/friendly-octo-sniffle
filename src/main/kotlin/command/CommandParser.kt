package command

import model.Command

object CommandParser {
    fun parseCommand(command: List<String>): Command = try {
        when (command[0].uppercase()) {
            "GET" -> Command.Get(command[1])
            "SET" -> Command.Set(command[1], command[2])
            "DELETE" -> Command.Delete(command[1])
            "COUNT" -> Command.Count(command[1])
            "BEGIN" -> Command.Begin
            "ROLLBACK" -> Command.Rollback
            "COMMIT" -> Command.Commit
            else -> Command.Unknown(command[0])
        }
    } catch (e: IndexOutOfBoundsException) {
        Command.ParseError("not enough arguments when parsing command: ${command.joinToString(" ")}")
    }
}