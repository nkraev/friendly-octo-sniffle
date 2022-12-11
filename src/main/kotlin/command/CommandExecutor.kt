package command

import model.Command
import model.ExecutionResult
import model.KeyValueStorageInterface
import model.OperationResult

object CommandExecutor {
    fun execute(command: Command, storage: KeyValueStorageInterface) = command.performAction(storage)

    private fun Command.performAction(storage: KeyValueStorageInterface): ExecutionResult = when (this) {
        is Command.Get -> storage.get(field).toExecutionResult()
        is Command.Set -> storage.set(field, value).toExecutionResult()
        is Command.Delete -> storage.delete(field).toExecutionResult()
        is Command.Count -> storage.count(field).toExecutionResult()
        is Command.Begin -> storage.begin().toExecutionResult()
        is Command.Rollback -> storage.rollback().toExecutionResult()
        is Command.Commit -> storage.commit().toExecutionResult()
        is Command.Unknown -> ExecutionResult.Error("Can't parse command: $instruction")
    }

    private fun OperationResult.toExecutionResult() = when (this) {
        is OperationResult.Success<*> -> {
            val message = if (result != Unit) "$result" else null
            ExecutionResult.Success(message)
        }

        is OperationResult.Error -> ExecutionResult.Error(message)
    }

    private fun Unit.toExecutionResult() = ExecutionResult.Success()
}