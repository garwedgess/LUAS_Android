package eu.wedgess.luas.domain

/**
 * Interface for model mappers
 */
interface Mapper<E, D> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(data: D): E

}