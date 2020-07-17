package eu.wedgess.luas.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.wedgess.luas.domain.model.StopInformationEntity
import eu.wedgess.luas.domain.usecases.GetMarlboroughForecastUseCase
import eu.wedgess.luas.domain.usecases.GetStillorganForecastUseCase
import eu.wedgess.luas.presentation.model.Resource
import eu.wedgess.luas.presentation.model.StationsEnum
import eu.wedgess.luas.presentation.utils.TimeCheckUtility
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

// Inject our dispatchers for unit testing
class TramForecastViewModel @Inject constructor(
    private val getStillorganForecastUseCase: GetStillorganForecastUseCase,
    private val getMarlboroughForecastUseCase: GetMarlboroughForecastUseCase,
    private val timeChecker: TimeCheckUtility,
    private val dispatcherIo: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) :
    ViewModel() {

    private val stopForecastResult: MutableLiveData<Resource<StopInformationEntity>> =
        MutableLiveData()
    private val stopNameResult: MutableLiveData<StationsEnum> = MutableLiveData()

    // handle the exception from the coroutines for both tram forecasts depending on which one is used
    private val tramForecastCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        run {
            Timber.e(throwable)
            stopForecastResult.postValue(Resource.error(throwable.message ?: "Unknown error"))
        }
    }


    init {
        stopForecastResult.value = Resource.loading()
    }

    private fun getStillorganTramForecast() {
        viewModelScope.launch(dispatcherIo + tramForecastCoroutineExceptionHandler) {
            stopForecastResult.postValue(Resource.success(getStillorganForecastUseCase.execute()))
        }
    }

    private fun getMarlboroughTramForecast() {
        viewModelScope.launch(dispatcherIo + tramForecastCoroutineExceptionHandler) {
            stopForecastResult.postValue(Resource.success(getMarlboroughForecastUseCase.execute()))
        }
    }

    private fun getStationToRetrieve(): StationsEnum {
        timeChecker.getHourAndMinutesOfDay().apply {
            val hour = requireNotNull(this[TimeCheckUtility.HOUR])
            val minutes = requireNotNull(this[TimeCheckUtility.MINUTES])
            return if (((hour == 12 && minutes >= 1) || hour > 12) && (hour <= 23 && minutes <= 59)) {
                StationsEnum.STILLORGAN
            } else {
                StationsEnum.MARLBOROUGH
            }
        }
    }


    fun getTramsForecast() {
        viewModelScope.launch(dispatcherMain) {
            getStationToRetrieve().let { station ->
                stopNameResult.value = station
                when (station) {
                    StationsEnum.STILLORGAN -> getStillorganTramForecast()
                    StationsEnum.MARLBOROUGH -> getMarlboroughTramForecast()
                }
            }

        }
    }

    fun getStopForecastResult() = stopForecastResult
    fun getStopNameResult() = stopNameResult

}