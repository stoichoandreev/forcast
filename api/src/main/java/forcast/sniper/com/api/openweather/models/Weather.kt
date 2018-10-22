package forcast.sniper.com.api.openweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Weather: Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("main")
    @Expose
    lateinit var main: String

    @SerializedName("description")
    @Expose
    lateinit var description: String

    @SerializedName("icon")
    @Expose
    lateinit var icon: String

}
