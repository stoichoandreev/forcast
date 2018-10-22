package forcast.sniper.com.api.openweather

import forcast.sniper.com.api.ApiConfiguration

class OpenWeatherApiConfiguration(): ApiConfiguration {

    override val baseURL: String
        get() = "http://api.openweathermap.org/"
}
