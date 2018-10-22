package forcast.sniper.com.forcast.view

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.tbruyelle.rxpermissions2.RxPermissions
import forcast.sniper.com.forcast.ForCastApplication
import forcast.sniper.com.forcast.R
import forcast.sniper.com.forcast.dagger.components.DaggerMainActivityComponent
import forcast.sniper.com.forcast.mvp.MainActivityPresenter
import forcast.sniper.com.forcast.viewmodels.MainActivityViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.content.Intent
import forcast.sniper.com.forcast.constants.LocationConstants
import forcast.sniper.com.forcast.dagger.modules.MainActivityModule
import forcast.sniper.com.forcast.recivers.AddressResultReceiver
import forcast.sniper.com.forcast.services.FetchAddressIntentService

class MainActivity : AppCompatActivity(), MainActivityPresenter.View, AddressResultReceiver.AddressResultListener {

    @Inject
    lateinit var presenter: MainActivityPresenter
    //location client
    @Inject
    lateinit var locationClient: FusedLocationProviderClient
    //Receiver to get the response from FetchAddressIntentService
    @Inject
    lateinit var addressResultReceiver: AddressResultReceiver

    private var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        inject()

        askLocationPermissions()

    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    @SuppressLint("MissingPermission")
    private fun askLocationPermissions() {
        val rxPermissions = RxPermissions(this)
        disposable = rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe { granted ->
                    if (granted!!) {
                        no_location.visibility = View.GONE
                        forecast_view_container.visibility = View.VISIBLE
                        locationClient.lastLocation.addOnSuccessListener{ location : Location? ->
                            when(location) {
                                null -> presenter.fetchForecast("London")//set default location
                                else -> getLastKnownLocation(location)
                            }
                        }
                    } else {
                        showLocationPermissionsDenied()
                        no_location.visibility = View.VISIBLE
                        forecast_view_container.visibility = View.GONE
                    }
                }
    }

    private fun getLastKnownLocation(lastLocation : Location) {
        val intent = Intent(this, FetchAddressIntentService::class.java)
        intent.putExtra(LocationConstants.RECEIVER, addressResultReceiver)
        intent.putExtra(LocationConstants.LOCATION_DATA_EXTRA, lastLocation)
        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent)
    }

    private fun showLocationPermissionsDenied() {
        Toast.makeText(this, "Location permissions needed", Toast.LENGTH_LONG).show()
    }

    private fun inject() {
        DaggerMainActivityComponent.builder()
                .applicationComponent(ForCastApplication.getApplicationComponent())
                .mainActivityModule(MainActivityModule(this))
                .build()
                .inject(this)
    }

    override fun showForecastResult(result: MainActivityViewModel) {
        city_name_view.text = result.city
        forecast_view.text = result.forecast
    }

    override fun showLoader(show: Boolean) {
        progress_indicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onAddressFound(addressOutput: String) {
        presenter.fetchForecast(addressOutput)
    }

    override fun onAddressNotFound(error: String) {
        showError(error)
    }
}
