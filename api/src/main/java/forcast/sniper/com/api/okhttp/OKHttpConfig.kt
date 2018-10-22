package forcast.sniper.com.api.okhttp

interface OKHttpConfig {

    val connectTimeout: Long

    val readTimeout: Long

    val writeTimeout: Long
}
