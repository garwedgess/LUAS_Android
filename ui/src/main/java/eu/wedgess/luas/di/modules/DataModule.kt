package eu.wedgess.luas.di.modules;

import dagger.Module;
import dagger.Provides
import eu.wedgess.luas.data.api.LuasAPI
import eu.wedgess.luas.data.mappers.StopInformationMapper
import eu.wedgess.luas.data.repository.LuasForecastRepositoryImpl
import eu.wedgess.luas.di.scopes.ApplicationScope
import eu.wedgess.luas.domain.LuasForecastRepository

@Module(includes = [MapperModule::class])
class DataModule {

    @ApplicationScope
    @Provides
    fun provideLuasForecastRepository(
        luasAPI: LuasAPI,
        stopInformationMapper: StopInformationMapper
    ): LuasForecastRepository {
        return LuasForecastRepositoryImpl(
            luasAPI, stopInformationMapper
        )
    }
}