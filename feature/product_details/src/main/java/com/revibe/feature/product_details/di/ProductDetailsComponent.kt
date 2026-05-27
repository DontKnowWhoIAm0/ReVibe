package com.revibe.feature.product_details.di

import com.revibe.feature.product_details.presentation.ProductDetailsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ProductDetailsModule::class],
    dependencies = [ProductDetailsDependencies::class]
)
interface ProductDetailsComponent {

    fun productDetailsViewModel(): ProductDetailsViewModel

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ProductDetailsDependencies,
            @BindsInstance @ArticleId article: String,
            @BindsInstance @UserId userId: String,
            @BindsInstance @IsFavourite isFavourite: Boolean
        ): ProductDetailsComponent
    }
}