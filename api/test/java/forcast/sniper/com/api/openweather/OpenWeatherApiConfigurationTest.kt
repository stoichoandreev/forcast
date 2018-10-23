package forcast.sniper.com.api.openweather

import org.junit.Test

import org.junit.Assert.*

class OpenWeatherApiConfigurationTest {

    @Test
    fun `test Open Weather API configuration has correct base URL`() {
        //given
        val tested = OpenWeatherApiConfiguration()
        val expected = "http://api.openweathermap.org/"
        //when
        val result = tested.baseURL
        //test
        assertEquals(expected, result)
    }
}