import impl.InMemoryMapKeyValueStorage
import model.OperationResult
import kotlin.test.Test
import kotlin.test.assertEquals

class KeyValueStoreUnitTest {
    @Test
    fun verifyCommitClearsVersion() {
        val storage = InMemoryMapKeyValueStorage()

        storage.set("abc", "123")
        storage.begin()
        storage.set("abc", "456")
        storage.commit()

        val result = (storage.dump() as? OperationResult.Success<*>)?.result ?: ""
        val assumption = listOf("0: abc=456", "1: ").joinToString("\n")

        assertEquals(result, assumption)
    }
}