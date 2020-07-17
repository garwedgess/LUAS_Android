package eu.wedgess.luas.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.wedgess.luas.features.forecast.TramForecastActivity
import eu.wedgess.luas.di.modules.TramForecastActivityModule
import eu.wedgess.luas.di.modules.TramForecastViewModelModule
import eu.wedgess.luas.di.modules.ViewModelFactoryModule
import eu.wedgess.luas.di.scopes.ActivityScope

@Module(includes = [ViewModelFactoryModule::class])
interface ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [TramForecastActivityModule::class, TramForecastViewModelModule::class])
    fun bindMainActivity(): TramForecastActivity
}