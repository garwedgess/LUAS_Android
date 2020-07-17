package eu.wedgess.luas.data.api

import eu.wedgess.luas.data.model.StopInformationData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for the Luas API endpoints, using Retrofit 2.
 */
interface LuasAPI {


    @GET("get.ashx")
    suspend fun fetchTramForecast(
        @Query("action") action: String = "forecast",
        @Query("stop") stop: String,
        @Query("encrypt") encrypt: Boolean = false
    ): StopInformationData


    companion object {

        const val BASE_URL = "http://luasforecasts.rpa.ie/xml/"
    }
}