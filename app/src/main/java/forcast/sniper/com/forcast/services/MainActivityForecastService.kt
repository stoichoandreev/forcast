package forcast.sniper.com.forcast.services

import forcast.sniper.com.api.screens.MainScreenApi
import forcast.sniper.com.forcast.converter.MainActivityViewModelConverter
import forcast.sniper.com.forcast.viewmodels.MainActivityViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler

class MainActivityForecastService(private val api: MainScreenApi,
                                  private val converter: MainActivityViewModelConverter,
                                  private val notifications: Scheduler,
                                  private val worker: Scheduler) {

    fun getForecast(cityName: String, cityId: String): Observable<MainActivityViewModel> {
        return api.forecast(cityId)
                .map { response -> converter.extractFromForecastResponse(response, cityName) }
                .subscribeOn(worker)
                .observeOn(notifications)
    }

}
