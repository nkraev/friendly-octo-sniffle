package command

import model.Command
import model.ExecutionResult
import model.KeyValueStorageInterface
import model.OperationResult

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

        is Command.Count -> {
            val result = storage.count(command.field)
            ExecutionResult.Success("$result")
        }

        is Command.Begin -> {
            storage.begin()
            ExecutionResult.Success()
        }

        is Command.Rollback -> storage.rollback().toExecutionResult()

        is Command.Commit -> storage.commit().toExecutionResult()

        is Command.Unknown -> ExecutionResult.Error("Can't parse command: ${command.instruction}")
    }

    private fun OperationResult.toExecutionResult() = when (this) {
        is OperationResult.Success -> ExecutionResult.Success()
        is OperationResult.Error -> ExecutionResult.Error(message)
    }
}