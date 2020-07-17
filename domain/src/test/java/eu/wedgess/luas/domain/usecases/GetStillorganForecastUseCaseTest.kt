package eu.wedgess.luas.domain.usecases

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import eu.wedgess.luas.domain.LuasForecastRepository
import eu.wedgess.luas.mock.getMockStillorganStopInformationEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetStillorganForecastUseCaseTest {

    @Mock
    private lateinit var luasForecastRepository: LuasForecastRepository
    private lateinit var getStillorganForecastUseCase: GetStillorganForecastUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getStillorganForecastUseCase = GetStillorganForecastUseCase(luasForecastRepository)
    }

    @Test
    fun `executing getStillorganForecastUseCase calls repository getStillorganForecast once`() {
        runBlocking {
            whenever(luasForecastRepository.getMarlboroughForecast())
                .thenReturn(getMockStillorganStopInformationEntity())

            getStillorganForecastUseCase.execute()
            verify(luasForecastRepository, times(1)).getStillorganForecast()
            verifyNoMoreInteractions(luasForecastRepository)
        }
    }
}