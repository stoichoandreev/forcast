package forcast.sniper.com.api.screens;

import forcast.sniper.com.api.openweather.models.ForecastResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainScreenApi {

    @GET("data/2.5/forecast")
    Observable<ForecastResponse> forecast(@Query("id") String cityId);

}
