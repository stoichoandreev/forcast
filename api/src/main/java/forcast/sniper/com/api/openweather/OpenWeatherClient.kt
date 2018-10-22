package forcast.sniper.com.api.openweather

import forcast.sniper.com.api.ApiConfiguration
import forcast.sniper.com.api.RetrofitClient
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

class OpenWeatherClient(converterFactory: Converter.Factory,
                        callAdapterFactory: CallAdapter.Factory,
                        okHttpClient: OkHttpClient,
                        config: ApiConfiguration) : RetrofitClient {

    private val restAdapter: Retrofit = Retrofit.Builder()
            .baseUrl(config.baseURL)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .validateEagerly(true)
            .build()

    companion object {
        const val NAME = "OpenWeatherClient"
        const val GSON_CONVERTER_FACTORY = "OpenWeatherConverterFactory"
        const val RX_CALL_ADAPTER_FACTORY = "OpenWeatherCallAdapterFactory"
        const val API_CONFIGURATION = "OpenWeatherApiConfiguration"
    }

    override fun <T> api(service: Class<T>): T {
        return restAdapter.create(service)
    }
}
