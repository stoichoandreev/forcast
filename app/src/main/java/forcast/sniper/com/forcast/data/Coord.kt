package forcast.sniper.com.forcast.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Coord: Serializable {

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

}
