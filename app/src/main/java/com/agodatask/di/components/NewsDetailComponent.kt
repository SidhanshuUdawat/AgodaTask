package com.agodatask.di.components

import com.agodatask.di.modules.NewsDetailModule
import com.agodatask.di.provider.ApplicationBaseComponent
import com.agodatask.di.scope.PerActivity
import com.agodatask.main.news.detail.NewsDetailActivity
import dagger.Component

/**
 * Created by Sid on 30/06/2018.
 */

@PerActivity
@Component(dependencies = [(ApplicationBaseComponent::class)], modules = [(NewsDetailModule::class)])
interface NewsDetailComponent {
    fun inject(newsDetailActivity: NewsDetailActivity)
}