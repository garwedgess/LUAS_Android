package eu.wedgess.luas.domain.usecases

abstract class BaseUseCase<Type> {

    abstract suspend fun execute(): Type
}