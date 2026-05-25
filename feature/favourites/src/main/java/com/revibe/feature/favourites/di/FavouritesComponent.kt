package com.revibe.feature.favourites.di

import com.revibe.feature.favourites.presentation.FavouritesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FavouritesModule::class],
    dependencies = [FavouritesDependencies::class]
)
interface FavouritesComponent {

    fun favouritesViewModel(): FavouritesViewModel

    @Component.Factory
    interface Factory {
        fun create(dependencies: FavouritesDependencies): FavouritesComponent
    }
}