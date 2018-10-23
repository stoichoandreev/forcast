package forcast.sniper.com.forcast.mvp

import forcast.sniper.com.forcast.viewmodels.MainActivityViewModel

interface MainActivityPresenter {

    fun attachView(view: MainActivityPresenter.View)

    fun detachView()

    fun destroy()

    fun fetchForecast(addressOutput: String)

    interface View {

        fun showForecastResult(result: MainActivityViewModel)

        fun showLoader(show: Boolean)

        fun showError(message: String)

    }
}
