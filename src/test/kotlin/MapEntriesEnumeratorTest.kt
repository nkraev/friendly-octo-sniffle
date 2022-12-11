import extensions.collectVersioned
import kotlin.test.Test
import kotlin.test.assertEquals

class MapEntriesEnumeratorTest {
    @Test
    fun checkOverrideOrderInMap() {
        val map = mapOf(
            1 to mapOf("abc" to "123", "def" to "456", "xyz" to "hello"),
            2 to mapOf("def" to "789"),
            3 to mapOf("xyz" to "world!"),
            4 to mapOf("def" to "111", "foo" to "bar")
        )

        val result = map.collectVersioned(4)
        val expected = mapOf(
            "abc" to "123",
            "def" to "111",
            "xyz" to "world!",
            "foo" to "bar"
        )
        assertEquals(result, expected)
    }
}