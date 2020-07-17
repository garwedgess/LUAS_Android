package eu.wedgess.luas.mock

import okhttp3.mockwebserver.MockResponse
import java.net.HttpURLConnection

object TramForecastMockResponses: XmlResponseLoader() {


    fun getSuccessfulStillorganForecastsResponse(): MockResponse {
        return MockResponse().apply {
            setHeader("Content-Type", "text/xml")
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(getXml(path = "$ROOT_XML_DIRECTORY/stillorgan_trams.xml"))
        }
    }

    fun getSuccessfulMarlboroughForecastsResponse(): MockResponse {
        return MockResponse().apply {
            setHeader("Content-Type", "text/xml")
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(getXml(path = "$ROOT_XML_DIRECTORY/marlborough_trams.xml"))
        }
    }

}