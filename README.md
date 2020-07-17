# LUAS Android Demo
Example multi-module Android App written in Kotlin using clean architecture, coroutines & MVVM.

### The 4 layers are:
- **data**: *includes API, repository implementations, model, mappers and date utility classes*
- **domain**: *includes usecases and interfaces for the mapper and repository*
- **presentation**: *includes ViewModels*
- **ui**: *includes activities and resources*

------------------------------

### Testing
#### The project includes unit tests for the data, domain and presentation layers using:
- Mockito Kotlin
- Mock Web server (Data layer only)

Note that there is a module named **mock**. The mock module is used by the other modules for mocking return values when using mockito in unit tests.
