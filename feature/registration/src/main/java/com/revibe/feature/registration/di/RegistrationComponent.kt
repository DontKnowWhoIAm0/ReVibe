package com.revibe.feature.registration.di

import com.revibe.feature.registration.presentation.RegistrationViewModel
import com.revibe.core.data.di.DataStoreModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RegistrationModule::class, DataStoreModule::class],
    dependencies = [RegistrationDependencies::class]
)
interface RegistrationComponent {

    fun registrationViewModel(): RegistrationViewModel

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: RegistrationDependencies,
            dataStoreModule: DataStoreModule
        ): RegistrationComponent
    }
}