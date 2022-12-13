## Implementation assumptions and known issues

* We won't have more than max int32 records in the storage for this task
* Very limited thread-safety (it's basically absent), but i hope to fix it later

## Resolved issues 
* **[fixed]** ~~We're (currently?) missing fail-safe instructions parsing, assuming all instructions would be provided correctly~~
* **[fixed]** ~~Running app via gradle wrapper isn't working (both tests are empty and main crashes because of EOF already being reached)~~
* **[fixed]** ~~Commands are (currently) case-sensitive~~

## Running instructions

**Run [Main.kt](src%2Fmain%2Fkotlin%2FMain.kt) / [InMemoryMapKeyValueTestExecutor.kt](src%2Ftest%2Fkotlin%2FInMemoryMapKeyValueTestExecutor.kt) from the IntelliJ**

### OR 

### To run tests: 
```shell
./gradlew test
```

If some files were incorrect, test task would fail.

### Adding new tests
* Simply add a new file with the prefix `Test_` into the `src/test/resources`. The environment will pick it up and execute against the key-value store.

### To run app: 
```shell
./gradlew fatJar
java -cp build/libs/TrustWalletTakeHome-1.0-SNAPSHOT-standalone.jar MainKt
```

## Additional commands beyond required
* `quit` - stops the app :)
* `dump` - shows the contents of the store for all the versions
