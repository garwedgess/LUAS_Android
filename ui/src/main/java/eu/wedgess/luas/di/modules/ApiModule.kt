package eu.wedgess.luas.di.modules

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import eu.wedgess.luas.data.api.LuasAPI
import eu.wedgess.luas.di.scopes.ApplicationScope
import eu.wedgess.luas.data.util.TikXMLDateFormatter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*


@Module
class ApiModule {

    @ApplicationScope
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val tikxml = TikXml.Builder().exceptionOnUnreadXml(true)
            .addTypeConverter(Date::class.java,
                TikXMLDateFormatter()
            ).build()
        return Retrofit.Builder()
            .addConverterFactory(TikXmlConverterFactory.create(tikxml))
            .baseUrl(LuasAPI.BASE_URL)
            .client(client)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideLuasAPI(retrofit: Retrofit): LuasAPI {
        return retrofit.create(LuasAPI::class.java)
    }
}