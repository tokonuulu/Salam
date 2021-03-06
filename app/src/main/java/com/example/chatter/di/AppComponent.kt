package com.example.chatter.di

import android.app.Application
import com.example.chatter.ChatterApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        ActivityViewModelModule::class]
)
interface AppComponent : AndroidInjector<ChatterApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}