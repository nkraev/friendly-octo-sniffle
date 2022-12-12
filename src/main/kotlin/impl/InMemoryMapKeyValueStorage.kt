package impl

import extensions.collectVersioned
import model.KeyValueStorageInterface
import model.OperationResult

class InMemoryMapKeyValueStorage : KeyValueStorageInterface {
    private val versions = mutableMapOf<Int, Map<String, String>>()
    private var currentVersion: Int = 0

    override fun get(key: String): OperationResult = when (val result = getCurrentMapVersion()[key]) {
        null -> OperationResult.Error("key not set")
        else -> OperationResult.Success(result)
    }

    override fun set(key: String, value: String) {
        val sourceMap = getCurrentMapVersion()
        updateCurrentMapVersion(sourceMap + (key to value))
    }

    override fun delete(key: String) {
        val sourceMap = getCurrentMapVersion()
        updateCurrentMapVersion(sourceMap - key)
    }

    override fun count(value: String): OperationResult =
        getCurrentMapVersion().values.count { it == value }.let { OperationResult.Success(it) }

    override fun begin() {
        currentVersion += 1
    }

    override fun commit(): OperationResult {
        if (currentVersion == 0) {
            return OperationResult.Error("no transaction")
        }
        val previousVersion = getPreviousVersion()
        val previousMap = versions[previousVersion] ?: emptyMap()
        versions[previousVersion] = previousMap + getCurrentMapVersion()
        currentVersion = previousVersion
        return OperationResult.Success(Unit)
    }

    override fun rollback(): OperationResult {
        if (currentVersion == 0) {
            return OperationResult.Error("no transaction")
        }
        updateCurrentMapVersion(emptyMap())
        currentVersion = getPreviousVersion()
        return OperationResult.Success(Unit)
    }

    override fun dump(): OperationResult {
        val result = versions
            .map { (version, records) -> "$version: ${records.entries.joinToString()}" }
            .joinToString("\n")

        return OperationResult.Success(result)
    }

    private fun getCurrentMapVersion() = versions.collectVersioned(currentVersion)

    private fun updateCurrentMapVersion(newVersion: Map<String, String>) {
        versions[currentVersion] = newVersion
    }

    private fun getPreviousVersion(): Int = when (currentVersion) {
        0 -> 0
        else -> currentVersion - 1
    }
}