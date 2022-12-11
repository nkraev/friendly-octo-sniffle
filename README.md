## Implementation assumptions and known issues

* We won't have more than max int32 records in the storage for this task
* **[fixed]** ~~We're (currently?) missing fail-safe instructions parsing, assuming all instructions would be provided correctly~~
* Running app via gradle wrapper isn't working (both tests are empty and main crashes because of EOF already being reached)
* **[fixed]** ~~Commands are (currently) case-sensitive~~
* Very limited thread-safety (it's basically absent), but i hope to fix it later
