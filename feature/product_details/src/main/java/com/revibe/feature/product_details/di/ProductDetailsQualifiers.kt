package com.revibe.feature.product_details.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ArticleId

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserId

@Qualifier @Retention(AnnotationRetention.BINARY)
annotation class IsFavourite