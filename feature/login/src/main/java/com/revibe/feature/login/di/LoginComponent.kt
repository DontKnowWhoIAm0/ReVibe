package com.revibe.feature.login.di

import com.revibe.core.data.di.DataStoreModule
import com.revibe.feature.login.presentation.LoginViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [LoginModule::class, DataStoreModule::class],
    dependencies = [LoginDependencies::class]
)
interface LoginComponent {

    fun loginViewModel(): LoginViewModel

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: LoginDependencies,
            dataStoreModule: DataStoreModule
        ): LoginComponent
    }
}