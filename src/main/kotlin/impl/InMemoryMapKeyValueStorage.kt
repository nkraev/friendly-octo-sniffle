package impl

import model.KeyValueStorageInterface

class InMemoryMapKeyValueStorage : KeyValueStorageInterface {
    private val map = mutableMapOf<String, String>()
    override fun set(key: String, value: String) = map.set(key, value)

    override fun get(key: String): String? = map[key]

    override fun delete(key: String) {
        map.remove(key)
    }

    override fun count(value: String): Int =
        map.values.count { it == value }

    override fun begin() {
        TODO("Not yet implemented")
    }

    override fun commit() {
        TODO("Not yet implemented")
    }

    override fun rollback() {
        TODO("Not yet implemented")
    }

}