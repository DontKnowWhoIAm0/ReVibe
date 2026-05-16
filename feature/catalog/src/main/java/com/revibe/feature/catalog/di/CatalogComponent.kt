package com.revibe.feature.catalog.di

import com.revibe.feature.catalog.presentation.CatalogViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CatalogModule::class],
    dependencies = [CatalogDependencies::class]
)
interface CatalogComponent {

    fun catalogViewModel(): CatalogViewModel

    @Component.Factory
    interface Factory {
        fun create(dependencies: CatalogDependencies): CatalogComponent
    }
}