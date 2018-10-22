package forcast.sniper.com.forcast.dagger.components

import dagger.Component
import forcast.sniper.com.forcast.dagger.modules.MainActivityModule
import forcast.sniper.com.forcast.dagger.scope.MainActivityScope
import forcast.sniper.com.forcast.view.MainActivity

@MainActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)

}
