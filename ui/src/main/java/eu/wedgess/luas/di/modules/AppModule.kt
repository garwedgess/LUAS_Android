package eu.wedgess.luas.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import eu.wedgess.luas.LuasApplication
import eu.wedgess.luas.di.scopes.ApplicationScope
import eu.wedgess.luas.presentation.utils.TimeCheckUtility
import javax.inject.Named

@Module(includes = [AppModule.BindsModule::class])
class AppModule {

    @Module
    interface BindsModule {
        @Binds
        fun application(app: LuasApplication): Application
    }

    @ApplicationScope
    @Provides
    internal fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}