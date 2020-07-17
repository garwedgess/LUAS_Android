package eu.wedgess.luas.domain.usecases

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import eu.wedgess.luas.domain.LuasForecastRepository
import eu.wedgess.luas.mock.getMockMarlboroughStopInformationEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetMarlboroughForecastUseCaseTest {

    @Mock
    private lateinit var luasForecastRepository: LuasForecastRepository
    private lateinit var getMarlboroughForecastUseCase: GetMarlboroughForecastUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMarlboroughForecastUseCase = GetMarlboroughForecastUseCase(luasForecastRepository)
    }

    @Test
    fun `executing getMarlboroughForecastUseCase calls repository getMarlboroughForecast once`() {
        runBlocking {
            whenever(luasForecastRepository.getMarlboroughForecast())
                .thenReturn(getMockMarlboroughStopInformationEntity())

            getMarlboroughForecastUseCase.execute()
            verify(luasForecastRepository, times(1)).getMarlboroughForecast()
            verifyNoMoreInteractions(luasForecastRepository)
        }
    }
}