package forcast.sniper.com.forcast

import android.app.Application

import forcast.sniper.com.forcast.dagger.components.ApplicationComponent
import forcast.sniper.com.forcast.dagger.components.DaggerApplicationComponent
import forcast.sniper.com.forcast.dagger.modules.ApplicationModule

class ForCastApplication : Application() {

    companion object {
        private lateinit var applicationComponent: ApplicationComponent
        fun getApplicationComponent(): ApplicationComponent = applicationComponent
    }


    override fun onCreate() {
        super.onCreate()

        initComponents()
    }

    fun initComponents() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }
}
