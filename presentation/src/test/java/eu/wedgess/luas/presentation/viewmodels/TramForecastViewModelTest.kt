package eu.wedgess.luas.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import eu.wedgess.luas.CoroutineTestRule
import eu.wedgess.luas.domain.model.StopInformationEntity
import eu.wedgess.luas.domain.usecases.GetMarlboroughForecastUseCase
import eu.wedgess.luas.domain.usecases.GetStillorganForecastUseCase
import eu.wedgess.luas.mock.getMockMarlboroughStopInformationEntity
import eu.wedgess.luas.mock.getMockStillorganStopInformationEntity
import eu.wedgess.luas.presentation.model.Resource
import eu.wedgess.luas.presentation.utils.TimeCheckUtility
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TramForecastViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var getStillorganForecastUseCase: GetStillorganForecastUseCase

    @Mock
    private lateinit var getMarlboroughForecastUseCase: GetMarlboroughForecastUseCase

    @Mock
    private lateinit var timeChecker: TimeCheckUtility

    private lateinit var viewModel: TramForecastViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel =
            TramForecastViewModel(
                getStillorganForecastUseCase,
                getMarlboroughForecastUseCase,
                timeChecker,
                coroutineRule.testDispatcher,
                coroutineRule.testDispatcher
            )

    }

    @Test
    fun `on init assert that getStopForecastResult returns loading`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given ViewModel init
            // Then
            assert(viewModel.getStopForecastResult().value == Resource.loading<StopInformationEntity>())
        }
    }

    @Test
    fun `when getStopForecast is executed at 12 01 getStillorganForecastUseCase is executed`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            whenever(timeChecker.getHourAndMinutesOfDay()).thenReturn(
                mapOf(
                    TimeCheckUtility.HOUR to 12,
                    TimeCheckUtility.MINUTES to 1
                )
            )
            // When
            viewModel.getStopForecast()
            // Then
            verify(getStillorganForecastUseCase, times(1)).execute()
        }
    }

    @Test
    fun `when getStopForecast is executed at 23 59 getStillorganForecastUseCase is executed`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            whenever(timeChecker.getHourAndMinutesOfDay()).thenReturn(
                mapOf(
                    TimeCheckUtility.HOUR to 23,
                    TimeCheckUtility.MINUTES to 59
                )
            )
            // When
            viewModel.getStopForecast()
            // Then
            verify(getStillorganForecastUseCase, times(1)).execute()
        }
    }

    @Test
    fun `when getStopForecast is executed at 00 00 getMarlboroughForecastUseCase is executed`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            whenever(timeChecker.getHourAndMinutesOfDay()).thenReturn(
                mapOf(
                    TimeCheckUtility.HOUR to 0,
                    TimeCheckUtility.MINUTES to 0
                )
            )
            // When
            viewModel.getStopForecast()
            // Then
            verify(getMarlboroughForecastUseCase, times(1)).execute()
        }
    }

    @Test
    fun `when getStopForecast is executed at 12 00 getMarlboroughForecastUseCase is executed`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            whenever(timeChecker.getHourAndMinutesOfDay()).thenReturn(
                mapOf(
                    TimeCheckUtility.HOUR to 12,
                    TimeCheckUtility.MINUTES to 0
                )
            )
            // When
            viewModel.getStopForecast()
            // Then
            verify(getMarlboroughForecastUseCase, times(1)).execute()
        }
    }


    @Test
    fun `when getStopForecast is executed at 2 22 assert marlborough data is correct`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            val expectedData = getMockMarlboroughStopInformationEntity()
            whenever(timeChecker.getHourAndMinutesOfDay()).thenReturn(
                mapOf(
                    TimeCheckUtility.HOUR to 2,
                    TimeCheckUtility.MINUTES to 22
                )
            )
            whenever(getMarlboroughForecastUseCase.execute()).thenReturn(expectedData)
            // When
            viewModel.getStopForecast()
            // Then
            assert(viewModel.getStopForecastResult().value == Resource.success(expectedData))
        }
    }

    @Test
    fun `when getStopForecast is executed at 20 30 assert stillorgan data is correct`() {
        coroutineRule.testDispatcher.runBlockingTest {
            // Given
            val expectedData = getMockStillorganStopInformationEntity()
            whenever(timeChecker.getHourAndMinutesOfDay()).thenReturn(
                mapOf(
                    TimeCheckUtility.HOUR to 20,
                    TimeCheckUtility.MINUTES to 30
                )
            )
            whenever(getStillorganForecastUseCase.execute()).thenReturn(expectedData)
            // When
            viewModel.getStopForecast()
            // Then
            assert(viewModel.getStopForecastResult().value == Resource.success(expectedData))
        }
    }

}