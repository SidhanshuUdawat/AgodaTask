package com.agodatask.di.components

import com.agodatask.di.modules.NewsModule
import com.agodatask.di.provider.ApplicationBaseComponent
import com.agodatask.di.scope.PerActivity
import com.agodatask.main.news.NewsActivity
import dagger.Component

/**
 * Created by Sid on 27/06/2018.
 */

@PerActivity
@Component(dependencies = [(ApplicationBaseComponent::class)], modules = [(NewsModule::class)])
interface NewsComponent {
    fun inject(newsActivity: NewsActivity)
}
