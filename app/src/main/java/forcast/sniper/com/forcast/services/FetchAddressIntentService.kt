package forcast.sniper.com.forcast.services

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.text.TextUtils
import android.location.Geocoder
import android.location.Location
import forcast.sniper.com.forcast.constants.LocationConstants
import java.io.IOException
import java.util.*
import android.os.Bundle
import android.os.ResultReceiver
import forcast.sniper.com.forcast.R

class FetchAddressIntentService: IntentService(TAG) {

    companion object {
        private const val TAG = "FetchAddressIS"
    }

    private var receiver: ResultReceiver? = null

    override fun onHandleIntent(intent: Intent) {
        var errorMessage = getString(R.string.invalid_address)
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null

        receiver = intent.getParcelableExtra(LocationConstants.RECEIVER) as ResultReceiver?
                ?: throw IllegalArgumentException("No receiver passed. Please provide receiver for the results.")

        val location = intent.getParcelableExtra(LocationConstants.LOCATION_DATA_EXTRA) as Location?
                ?: throw IllegalArgumentException("Please provide last known location")

        // Errors could still arise from using the Geocoder (for example, if there is no
        // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
        // simply not have an address for a location. In all these cases, we communicate with the
        // receiver using a resultCode indicating failure. If an address is found, we use a
        // resultCode indicating success.
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        } catch (ioException: IOException) {
            errorMessage = getString(R.string.invalid_address)
        } catch (illegalArgumentException: IllegalArgumentException) {
            errorMessage = getString(R.string.invalid_lat_long_used)
        }

        if (addresses == null || addresses.isEmpty()) {
            deliverResultToReceiver(LocationConstants.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            val addressFragments = arrayOfNulls<String>(2)
            val countryCode = address.countryCode
            val city = address.locality

            addressFragments[0] = countryCode
            addressFragments[1] = city

            deliverResultToReceiver(LocationConstants.SUCCESS_RESULT, TextUtils.join(System.getProperty("line.separator"), addressFragments))
        }
    }


    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle()
        bundle.putString(LocationConstants.RESULT_DATA_KEY, message)
        receiver?.send(resultCode, bundle)
    }
}