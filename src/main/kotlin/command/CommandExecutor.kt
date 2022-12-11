package command

import model.Command
import model.ExecutionResult
import model.KeyValueStorageInterface

object CommandExecutor {
    fun execute(command: Command, storage: KeyValueStorageInterface): ExecutionResult = when (command) {
        is Command.Get -> {
            when (val value = storage.get(command.field)) {
                null -> ExecutionResult.Error("key not set")
                else -> ExecutionResult.Success(value)
            }
        }

        is Command.Set -> {
            storage.set(command.field, command.value)
            ExecutionResult.Success()
        }

        is Command.Delete -> {
            storage.delete(command.field)
            ExecutionResult.Success()
        }

        is Command.Unknown -> ExecutionResult.Error("Can't parse command: ${command.instruction}")
    }
}