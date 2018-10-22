package forcast.sniper.com.forcast.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityIdMapper {

    @SerializedName("_id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("country")
    @Expose
    lateinit var country: String

    @SerializedName("coord")
    @Expose
    lateinit var coord: Coord
}