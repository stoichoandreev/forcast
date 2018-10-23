package forcast.sniper.com.api.okhttp

import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultOkHttpConfigTest {

    private val tested = DefaultOkHttpConfig()

    @Test
    fun `test getConnectTimeout returns expected timeout`() {
        //when
        val result = tested.connectTimeout
        //test
        assertEquals(30, result)
    }

    @Test
    fun `test getReadTimeout returns expected timeout`() {
        //when
        val result = tested.readTimeout
        //test
        assertEquals(30, result)
    }

    @Test
    fun `test getWriteTimeout returns expected timeout`() {
        //when
        val result = tested.writeTimeout
        //test
        assertEquals(30, result)
    }
}