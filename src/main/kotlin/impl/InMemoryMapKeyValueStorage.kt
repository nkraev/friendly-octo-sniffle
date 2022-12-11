package impl

import definitions.KeyValueStorageInterface

class InMemoryMapKeyValueStorage : KeyValueStorageInterface {
    override fun set(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun get(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun delete(key: String) {
        TODO("Not yet implemented")
    }

    override fun count(value: String): Int {
        TODO("Not yet implemented")
    }

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