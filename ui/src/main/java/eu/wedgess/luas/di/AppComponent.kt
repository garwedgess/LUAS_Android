package eu.wedgess.luas.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import eu.wedgess.luas.LuasApplication
import eu.wedgess.luas.di.modules.*
import eu.wedgess.luas.di.scopes.ApplicationScope

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        MapperModule::class,
        NetworkModule::class,
        ApiModule::class,
        AppModule::class,
        DataModule::class
    ]
)

interface AppComponent : AndroidInjector<LuasApplication> {
    override fun inject(application: LuasApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: LuasApplication): Builder

        fun build(): AppComponent
    }
}