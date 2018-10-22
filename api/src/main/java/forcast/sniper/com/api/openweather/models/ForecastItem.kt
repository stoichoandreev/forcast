package forcast.sniper.com.api.openweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class ForecastItem: Serializable {

    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("weather")
    @Expose
    lateinit var weather: List<Weather>

}
