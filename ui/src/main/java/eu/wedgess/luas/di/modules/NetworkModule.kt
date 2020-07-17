package eu.wedgess.luas.di.modules

import dagger.Module
import dagger.Provides
import eu.wedgess.luas.di.scopes.ApplicationScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module(includes = [ApiModule::class])
class NetworkModule {

    @ApplicationScope
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BASIC
//            })
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .writeTimeout(10, TimeUnit.SECONDS)
    }

    @ApplicationScope
    @Provides
    fun providerOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }
}
