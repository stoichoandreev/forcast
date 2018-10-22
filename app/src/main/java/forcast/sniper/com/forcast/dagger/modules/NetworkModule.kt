package forcast.sniper.com.forcast.dagger.modules

import android.support.annotation.NonNull
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import forcast.sniper.com.api.ApiConfiguration
import forcast.sniper.com.api.RetrofitClient
import forcast.sniper.com.api.okhttp.DefaultOkHttpConfig
import forcast.sniper.com.api.okhttp.OKHttpConfig
import forcast.sniper.com.api.openweather.OpenWeatherApiConfiguration
import forcast.sniper.com.api.openweather.OpenWeatherClient
import forcast.sniper.com.api.openweather.OpenWeatherClientInterceptor
import forcast.sniper.com.forcast.dagger.scope.ApplicationScope
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun providesOkHttpConfig(): OKHttpConfig {
        return DefaultOkHttpConfig()
    }

    @Provides
    @ApplicationScope
    @Named(HTTP_BODY_LOGGING)
    fun provideHttpBodyLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @ApplicationScope
    fun provideUserAgentInterceptor(): OpenWeatherClientInterceptor {
        return OpenWeatherClientInterceptor()
    }

    @Provides
    @ApplicationScope
    @Named(OK_HTTP_CLIENT)
    fun provideOkHttpClient(okHttpConfig: OKHttpConfig,
                            clientInterceptor: OpenWeatherClientInterceptor,
                            @Named(HTTP_BODY_LOGGING) loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(okHttpConfig.connectTimeout, TimeUnit.SECONDS)
                .readTimeout(okHttpConfig.readTimeout, TimeUnit.SECONDS)
                .writeTimeout(okHttpConfig.writeTimeout, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
        builder.interceptors().add(clientInterceptor)
        return builder.build()
    }

    @Provides
    @ApplicationScope
    @Named(DEFAULT_GSON)
    fun provideGSON(): Gson {
        return GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat(DateFormat.LONG)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create()
    }

    @Provides
    @ApplicationScope
    @Named(OpenWeatherClient.GSON_CONVERTER_FACTORY)
    fun provideGsonConverterFactory(@Named(DEFAULT_GSON) gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @ApplicationScope
    @Named(OpenWeatherClient.RX_CALL_ADAPTER_FACTORY)
    fun provideRxCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @ApplicationScope
    @Named(OpenWeatherClient.API_CONFIGURATION)
    fun provideApiConfiguration(): ApiConfiguration {
        return OpenWeatherApiConfiguration()
    }

    @Provides
    @ApplicationScope
    @Named(OpenWeatherClient.NAME)
    fun provideOpenWeatherClient(@Named(OpenWeatherClient.GSON_CONVERTER_FACTORY) converterFactory: Converter.Factory,
                            @Named(OpenWeatherClient.RX_CALL_ADAPTER_FACTORY) callAdapterFactory: CallAdapter.Factory,
                            @Named(OK_HTTP_CLIENT) okHttpClient: OkHttpClient,
                            @Named(OpenWeatherClient.API_CONFIGURATION) apiConfig: ApiConfiguration): RetrofitClient {
        return OpenWeatherClient(converterFactory, callAdapterFactory, okHttpClient, apiConfig)
    }

    @Provides
    @ApplicationScope
    @Named(BACKGROUND_THREAD)
    fun provideBackgroundScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @ApplicationScope
    @Named(MAIN_THREAD)
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {

        const val HTTP_BODY_LOGGING = "HttpBodyLogging"
        const val OK_HTTP_CLIENT = "OkHttpClient"
        const val DEFAULT_GSON = "DefaultGSONSetup"
        const val BACKGROUND_THREAD = "BackgroundThread"
        const val MAIN_THREAD = "MainThread"
    }

}
