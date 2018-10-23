package forcast.sniper.com.forcast.services

import android.content.Context
import com.google.gson.Gson
import forcast.sniper.com.forcast.data.CityIdMapper
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.io.IOException

open class CityIdService(private val context: Context,
                    private val notifications: Scheduler,
                    private val worker: Scheduler) {

    fun read(countryCode: String, cityName: String): Observable<String> {
        return getObservable(countryCode, cityName)
                .subscribeOn(worker)
                .observeOn(notifications)
    }

    private fun getObservable(countryCode: String, cityName: String): Observable<String> {
        return Observable.create { subscriber ->
            if (!subscriber.isDisposed) {
                val cityId = findCityId(countryCode, cityName)
                when (cityId) {
                    "" -> subscriber.onError(RuntimeException("Your city is not supported"))
                    else -> subscriber.onNext(cityId)
                }
                subscriber.onComplete()
            }
        }
    }

    private fun findCityId(countryCode: String, cityName: String): String {
        val json: String?
        var result = ""
        try {
            val input = context.assets.open("city.list.json")
            json = input.bufferedReader().use {
                it.readText()
            }
            val data = Gson().fromJson(json, Array<CityIdMapper>::class.java)
            //we need better Search algorithm, but the json file records are not sorted well,
            //so that is way we can apply only Linear search algorithm
            for (item in data) {
                if (item.country == countryCode && item.name == cityName) {
                    result = item.id.toString()
                    break
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return result
    }

}
