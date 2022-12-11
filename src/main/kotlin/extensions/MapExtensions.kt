package extensions

fun Map<Int, Map<String, String>>.collectVersioned(lastVersion: Int): Map<String, String> =
    (0..lastVersion)
        .flatMap { version ->
            val mapForVersion = this[version] ?: emptyMap()
            mapForVersion.entries
        }
        .associate { (key, value) -> key to value }