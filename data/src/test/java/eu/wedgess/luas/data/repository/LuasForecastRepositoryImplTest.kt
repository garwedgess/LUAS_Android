package eu.wedgess.luas.data.repository

import eu.wedgess.luas.data.api.LuasAPI
import eu.wedgess.luas.data.mappers.StopInformationMapper
import eu.wedgess.luas.data.mappers.TramMapper
import eu.wedgess.luas.mock.TramForecastMockResponses
import eu.wedgess.luas.mock.getLuasApi
import eu.wedgess.luas.mock.getMockServer
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class LuasForecastRepositoryImplTest {

    private lateinit var luasForecastRepositoryImpl: LuasForecastRepositoryImpl
    private lateinit var luasApi: LuasAPI
    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {
        mockServer = getMockServer()
        luasApi = getLuasApi(mockServer)
        luasForecastRepositoryImpl = LuasForecastRepositoryImpl(
            luasApi, StopInformationMapper(
                TramMapper()
            )
        )
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `calling getStillorganForecast returns correct stopName`() {
        mockServer.enqueue(TramForecastMockResponses.getSuccessfulStillorganForecastsResponse())
        runBlocking {
            assert(luasForecastRepositoryImpl.getStillorganForecast().name == "Stillorgan")
        }
    }

    @Test
    fun `calling getMarlboroughForecast returns correct stopName`() {
        mockServer.enqueue(TramForecastMockResponses.getSuccessfulMarlboroughForecastsResponse())
        runBlocking {
            assert(luasForecastRepositoryImpl.getMarlboroughForecast().name == "Marlborough")
        }
    }
}
