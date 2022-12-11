package model

sealed class ExecutionResult {
    data class Success(val messageToPrint: String? = null) : ExecutionResult()
    data class Error(val errorMessage: String) : ExecutionResult()
}