package eu.wedgess.luas.di.modules

import dagger.Module
import dagger.Provides
import eu.wedgess.luas.di.scopes.ActivityScope
import eu.wedgess.luas.domain.LuasForecastRepository
import eu.wedgess.luas.domain.usecases.GetMarlboroughForecastUseCase
import eu.wedgess.luas.domain.usecases.GetStillorganForecastUseCase
import eu.wedgess.luas.presentation.utils.TimeCheckUtility

@Module
class TramForecastViewModelModule {

    @ActivityScope
    @Provides
    fun provideGetStillorganForecastBaseUseCase(
        luasForecastRepository: LuasForecastRepository
    ): GetStillorganForecastUseCase {
        return GetStillorganForecastUseCase(luasForecastRepository)
    }

    @ActivityScope
    @Provides
    fun provideGetMarlboroughForecastBaseUseCase(
        luasForecastRepository: LuasForecastRepository
    ): GetMarlboroughForecastUseCase {
        return GetMarlboroughForecastUseCase(luasForecastRepository)
    }

    @ActivityScope
    @Provides
    fun provideTimeCheckUtility() = TimeCheckUtility()
}