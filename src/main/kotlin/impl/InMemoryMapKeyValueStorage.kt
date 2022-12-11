package impl

import extensions.collectVersioned
import model.KeyValueStorageInterface

class InMemoryMapKeyValueStorage : KeyValueStorageInterface {
    private val versions = mutableMapOf<Int, Map<String, String>>()
    private var currentVersion: Int = 0

    override fun get(key: String): String? = getCurrentMapVersion()[key]

    override fun set(key: String, value: String) {
        val sourceMap = getCurrentMapVersion()
        updateCurrentMapVersion(sourceMap + (key to value))
    }

    override fun delete(key: String) {
        val sourceMap = getCurrentMapVersion()
        updateCurrentMapVersion(sourceMap - key)
    }

    override fun count(value: String): Int =
        getCurrentMapVersion().values.count { it == value }

    override fun begin() {
        currentVersion += 1
    }

    override fun commit() {
        if (currentVersion == 0) {
            // TODO: error
            return
        }
        val previousVersion = getPreviousVersion()
        val previousMap = versions[previousVersion] ?: emptyMap()
        versions[previousVersion] = previousMap + getCurrentMapVersion()
        currentVersion = previousVersion
    }

    override fun rollback() {
        if (currentVersion == 0) {
            // TODO: error
            return
        }
        updateCurrentMapVersion(emptyMap())
        currentVersion = getPreviousVersion()
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