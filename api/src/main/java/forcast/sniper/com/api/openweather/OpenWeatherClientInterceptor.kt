package forcast.sniper.com.api.openweather

import okhttp3.Interceptor
import okhttp3.Response

class OpenWeatherClientInterceptor : Interceptor {

    companion object {
        private const val API_KEY = "APPID"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter(API_KEY, "0bc9bc2a73fd9644f664cf5f5c5be8d7")
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
