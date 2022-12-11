## Implementation assumptions and known issues

* We won't have more than max int32 records in the storage for this task
* We're (currently?) missing fail-safe instructions parsing, assuming all instructions would be provided correctly
* Running app via gradle wrapper isn't working (both tests are empty and main crashes because of EOF already being reached)
* Commands are (currently) case-sensitive