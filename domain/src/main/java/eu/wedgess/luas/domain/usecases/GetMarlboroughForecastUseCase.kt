package eu.wedgess.luas.domain.usecases

import eu.wedgess.luas.domain.LuasForecastRepository
import eu.wedgess.luas.domain.model.StopInformationEntity
import javax.inject.Inject

class GetMarlboroughForecastUseCase @Inject constructor(private val repository: LuasForecastRepository) :
    BaseUseCase<StopInformationEntity>() {

    override suspend fun execute(): StopInformationEntity {
        return repository.getMarlboroughForecast()
    }

}