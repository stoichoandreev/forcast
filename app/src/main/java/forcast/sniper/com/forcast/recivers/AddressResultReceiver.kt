package forcast.sniper.com.forcast.recivers

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Bundle
import android.os.ResultReceiver
import forcast.sniper.com.forcast.constants.LocationConstants

@SuppressLint("RestrictedApi")
class AddressResultReceiver constructor(private val listener: AddressResultListener,
                                        handler: Handler?): ResultReceiver(handler) {

    interface AddressResultListener {

        fun onAddressFound(addressOutput: String)

        fun onAddressNotFound(error: String)

    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {

        // Display the address string or an error message sent from the intent service.
        val addressOutput = resultData.getString(LocationConstants.RESULT_DATA_KEY)

        when (resultCode) {
            LocationConstants.SUCCESS_RESULT -> listener.onAddressFound(addressOutput!!)
            LocationConstants.FAILURE_RESULT -> listener.onAddressNotFound(addressOutput!!)
        }
    }

}