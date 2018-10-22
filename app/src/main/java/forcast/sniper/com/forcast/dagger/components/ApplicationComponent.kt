package forcast.sniper.com.forcast.dagger.components

import android.content.Context

import javax.inject.Named

import dagger.Component
import forcast.sniper.com.api.RetrofitClient
import forcast.sniper.com.api.openweather.OpenWeatherClient
import forcast.sniper.com.forcast.ForCastApplication
import forcast.sniper.com.forcast.dagger.modules.ApplicationModule
import forcast.sniper.com.forcast.dagger.modules.NetworkModule
import forcast.sniper.com.forcast.dagger.scope.ApplicationScope
import io.reactivex.Scheduler

@ApplicationScope
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun context(): Context

    @Named(OpenWeatherClient.NAME)
    fun openWeatherClient(): RetrofitClient

    @Named(NetworkModule.MAIN_THREAD)
    fun mainScheduler(): Scheduler

    @Named(NetworkModule.BACKGROUND_THREAD)
    fun backgroundScheduler(): Scheduler

    fun inject(application: ForCastApplication)

}
