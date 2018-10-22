package forcast.sniper.com.forcast.dagger.modules

import android.content.Context
import android.os.Handler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import forcast.sniper.com.api.RetrofitClient
import forcast.sniper.com.api.openweather.OpenWeatherClient
import forcast.sniper.com.api.screens.MainScreenApi
import forcast.sniper.com.forcast.converter.MainActivityViewModelConverter
import forcast.sniper.com.forcast.dagger.scope.MainActivityScope
import forcast.sniper.com.forcast.mvp.MainActivityPresenter
import forcast.sniper.com.forcast.mvp.MainActivityPresenterImp
import forcast.sniper.com.forcast.recivers.AddressResultReceiver
import forcast.sniper.com.forcast.services.CityIdService
import forcast.sniper.com.forcast.services.MainActivityForecastService
import forcast.sniper.com.forcast.view.MainActivity
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module
class MainActivityModule(private val view: MainActivity) {

    @Provides
    @MainActivityScope
    fun providesLocationClient(application: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    @MainActivityScope
    fun providesAddressResultReceiver(): AddressResultReceiver {
        return AddressResultReceiver(view as AddressResultReceiver.AddressResultListener, Handler())
    }

    @Provides
    @MainActivityScope
    fun providesHomeSearchApi(@Named(OpenWeatherClient.NAME) openWeatherClient: RetrofitClient): MainScreenApi {
        return openWeatherClient.api(MainScreenApi::class.java)
    }

    @Provides
    @MainActivityScope
    fun providesForecastService(api: MainScreenApi,
                                         converter: MainActivityViewModelConverter,
                                         @Named(NetworkModule.MAIN_THREAD) notifications: Scheduler,
                                         @Named(NetworkModule.BACKGROUND_THREAD) worker: Scheduler): MainActivityForecastService {
        return MainActivityForecastService(api, converter, notifications, worker)
    }

    @Provides
    @MainActivityScope
    fun providesCityIdService(context: Context,
                                       @Named(NetworkModule.MAIN_THREAD) notifications: Scheduler,
                                       @Named(NetworkModule.BACKGROUND_THREAD) worker: Scheduler): CityIdService {
        return CityIdService(context, notifications, worker)
    }

    @Provides
    @MainActivityScope
    fun providesPresenter(remoteForecastService: MainActivityForecastService,
                                   cityIdService: CityIdService): MainActivityPresenter {
        return MainActivityPresenterImp(view as MainActivityPresenter.View,
                remoteForecastService,
                cityIdService,
                CompositeDisposable())
    }

}
