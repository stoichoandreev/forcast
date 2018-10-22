package forcast.sniper.com.api.openweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class ForecastResponse: Serializable {

    @SerializedName("cod")
    @Expose
    lateinit var cod: String

    @SerializedName("message")
    @Expose
    lateinit var message: String

    @SerializedName("list")
    @Expose
    lateinit var list: List<ForecastItem>

}
