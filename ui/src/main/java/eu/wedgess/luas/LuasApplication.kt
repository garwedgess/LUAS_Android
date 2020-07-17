package eu.wedgess.luas

import dagger.android.DaggerApplication
import eu.wedgess.luas.di.DaggerAppComponent
import timber.log.Timber

class LuasApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(LineNumberDebugTree())
        }
    }

    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        .build()


    class LineNumberDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
        }
    }
}