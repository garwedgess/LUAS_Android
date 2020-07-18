package eu.wedgess.luas.mock

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import eu.wedgess.luas.data.api.LuasAPI
import eu.wedgess.luas.data.util.TikXMLDateFormatter
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Helpers for Mock WebServer
 */

private fun getTikXML(): TikXml {
    return TikXml.Builder().exceptionOnUnreadXml(true)
        .addTypeConverter(
            Date::class.java,
            TikXMLDateFormatter()
        ).build()
}

private fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

}


fun getLuasApi(server: MockWebServer): LuasAPI {
    return Retrofit.Builder()
        .addConverterFactory(TikXmlConverterFactory.create(getTikXML()))
        .baseUrl(server.url("/"))
        .client(getOkHttpClient())
        .build()
        .create(LuasAPI::class.java)
}

fun getMockServer() = MockWebServer().apply {
    start()
}