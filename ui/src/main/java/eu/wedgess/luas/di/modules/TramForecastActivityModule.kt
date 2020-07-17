package eu.wedgess.luas.di.modules

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import eu.wedgess.luas.di.ViewModelKey
import eu.wedgess.luas.di.scopes.ActivityScope
import eu.wedgess.luas.domain.usecases.GetMarlboroughForecastUseCase
import eu.wedgess.luas.domain.usecases.GetStillorganForecastUseCase
import eu.wedgess.luas.presentation.utils.TimeCheckUtility
import eu.wedgess.luas.presentation.viewmodels.TramForecastViewModel
import kotlinx.coroutines.Dispatchers

@Module
class TramForecastActivityModule {

    @ActivityScope
    @Provides
    @IntoMap
    @ViewModelKey(TramForecastViewModel::class)
    fun provideForecastViewModel(
        getStillorganForecastUseCase: GetStillorganForecastUseCase,
        getMarlboroughForecastUseCase: GetMarlboroughForecastUseCase,
        timeCheckUtility: TimeCheckUtility
    ): ViewModel {
        return TramForecastViewModel(
            getStillorganForecastUseCase,
            getMarlboroughForecastUseCase,
            timeCheckUtility,
            Dispatchers.IO,
            Dispatchers.Main
        )
    }
}