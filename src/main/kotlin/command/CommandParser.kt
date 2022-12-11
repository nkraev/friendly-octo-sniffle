package command

import model.Command

object CommandParser {
    fun parseCommand(command: List<String>): Command = when (command[0]) {
        "GET" -> Command.Get(command[1])
        "SET" -> Command.Set(command[1], command[2])
        else -> Command.Unknown(command[0])
    }
}