package command

import model.Command

object CommandParser {
    fun parseCommand(command: List<String>): Command = when (command[0]) {
        "GET" -> Command.Get(command[1])
        "SET" -> Command.Set(command[1], command[2])
        "DELETE" -> Command.Delete(command[1])
        "COUNT" -> Command.Count(command[1])
        else -> Command.Unknown(command[0])
    }
}