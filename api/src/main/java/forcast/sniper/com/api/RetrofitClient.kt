package forcast.sniper.com.api

interface RetrofitClient {
    fun <T> api(service: Class<T>): T
}
