package eu.wedgess.luas.domain.usecases

import eu.wedgess.luas.domain.LuasForecastRepository
import eu.wedgess.luas.domain.model.StopInformationEntity
import javax.inject.Inject

class GetStillorganForecastUseCase @Inject constructor(private val repository: LuasForecastRepository) :
    BaseUseCase<StopInformationEntity>() {

    override suspend fun execute(): StopInformationEntity {
        return repository.getStillorganForecast()
    }

}