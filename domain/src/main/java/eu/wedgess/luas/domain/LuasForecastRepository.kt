package eu.wedgess.luas.domain

import eu.wedgess.luas.domain.model.StopInformationEntity

/**
 * Repository interface implemented in the data layer but resides in domain layer so our use cases
 * can call the repository without knowing about the data layer.
 */
interface LuasForecastRepository {

    suspend fun getStillorganForecast(): StopInformationEntity

    suspend fun getMarlboroughForecast(): StopInformationEntity
}