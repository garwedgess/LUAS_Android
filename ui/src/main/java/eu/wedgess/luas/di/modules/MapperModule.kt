package eu.wedgess.luas.di.modules

import dagger.Module
import dagger.Provides
import eu.wedgess.luas.data.mappers.StopInformationMapper
import eu.wedgess.luas.data.mappers.TramMapper
import eu.wedgess.luas.di.scopes.ApplicationScope

@Module
class MapperModule {

    @ApplicationScope
    @Provides
    fun provideTramMapper(): TramMapper {
        return TramMapper()
    }

    @ApplicationScope
    @Provides
    fun provideStopInformationMapper(tramMapper: TramMapper): StopInformationMapper {
        return StopInformationMapper(tramMapper)
    }
}