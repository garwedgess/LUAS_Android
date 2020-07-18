package eu.wedgess.luas.data.repository

import eu.wedgess.luas.data.api.LuasAPI
import eu.wedgess.luas.data.mappers.StopInformationMapper
import eu.wedgess.luas.domain.LuasForecastRepository
import eu.wedgess.luas.domain.model.StopInformationEntity
import javax.inject.Inject

class LuasForecastRepositoryImpl @Inject constructor(
    private val luasAPI: LuasAPI,
    private val stopInformationMapper: StopInformationMapper
) : LuasForecastRepository {

    override suspend fun getStillorganForecast(): StopInformationEntity {
        return stopInformationMapper.mapToEntity(luasAPI.fetchTramForecast(stopCode = "sti"))
    }

    override suspend fun getMarlboroughForecast(): StopInformationEntity {
        return stopInformationMapper.mapToEntity(luasAPI.fetchTramForecast(stopCode = "mar"))
    }

}