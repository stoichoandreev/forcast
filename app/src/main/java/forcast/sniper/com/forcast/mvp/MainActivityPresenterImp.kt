package forcast.sniper.com.forcast.mvp

import forcast.sniper.com.forcast.services.CityIdService
import forcast.sniper.com.forcast.services.MainActivityForecastService
import io.reactivex.disposables.CompositeDisposable

class MainActivityPresenterImp constructor(private var view: MainActivityPresenter.View? = null,
                                           private val remoteForecastService: MainActivityForecastService,
                                           private val cityIdService: CityIdService,
                                           private val disposables: CompositeDisposable) : MainActivityPresenter {

    override fun attachView(view: MainActivityPresenter.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun fetchForecast(addressOutput: String) {
        val addressFragments = addressOutput.split(System.getProperty("line.separator"))
        val countryCode = addressFragments[0]
        val cityName = addressFragments[1]
        view?.showLoader(true)
        disposables.add(cityIdService.read(countryCode, cityName)
                .flatMap { cityId -> remoteForecastService.getForecast(cityName, cityId) }
                .doOnComplete { view?.showLoader(false) }
                .subscribe({ response ->
                    view?.showForecastResult(response)
                }, { error ->
                    view?.showError(error.message!!)
                }))
    }

}
