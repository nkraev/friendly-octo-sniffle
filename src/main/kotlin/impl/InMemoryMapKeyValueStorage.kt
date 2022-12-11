package impl

import extensions.collectVersioned
import model.KeyValueStorageInterface

class InMemoryMapKeyValueStorage : KeyValueStorageInterface {
    private var storage = mapOf<String, String>()
    private val versions = mutableMapOf<Int, Map<String, String>>()
    private var currentVersion: Int? = null

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
        TODO("Not yet implemented")
    }

    override fun commit() {
        TODO("Not yet implemented")
    }

    override fun rollback() {
        TODO("Not yet implemented")
    }

    private fun getCurrentMapVersion(): Map<String, String> {
        val lastVersion = currentVersion ?: return storage
        return versions.collectVersioned(lastVersion)
    }

    private fun updateCurrentMapVersion(newVersion: Map<String, String>) {
        val version = currentVersion
        if (version != null) {
            versions[version] = newVersion
        } else {
            storage = newVersion
        }
    }
}