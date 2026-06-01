package com.revibe.feature.cart.di

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CartModule::class],
    dependencies = [CartDependencies::class]
)
interface CartComponent {
    fun cartViewModel(): CartViewModel

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: CartDependencies,
            @BindsInstance userId: String
        ): CartComponent
    }
}
