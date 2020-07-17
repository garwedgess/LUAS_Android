package eu.wedgess.luas.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import eu.wedgess.luas.presentation.ViewModelFactory

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}