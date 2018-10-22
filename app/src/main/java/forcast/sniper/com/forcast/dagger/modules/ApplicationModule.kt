package forcast.sniper.com.forcast.dagger.modules

import android.content.Context

import dagger.Module
import dagger.Provides
import forcast.sniper.com.forcast.dagger.scope.ApplicationScope

@Module
class ApplicationModule(private val appContext: Context) {

    @Provides
    @ApplicationScope
    fun provideAppContext(): Context = this.appContext

}
