package forcast.sniper.com.forcast.converter

import javax.inject.Inject

import forcast.sniper.com.api.openweather.models.ForecastResponse
import forcast.sniper.com.forcast.viewmodels.MainActivityViewModel

class MainActivityViewModelConverter @Inject constructor() {

    fun extractFromForecastResponse(response: ForecastResponse, cityName: String): MainActivityViewModel {
        val weather = response.list[0].weather[0].main + response.list[0].weather[0].description
        return MainActivityViewModel(cityName, weather)
    }

}
