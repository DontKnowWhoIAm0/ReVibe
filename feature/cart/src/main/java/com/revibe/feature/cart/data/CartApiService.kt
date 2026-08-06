package com.revibe.feature.cart.data

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface CartApiService {
    @POST("api/bookings")
    suspend fun createBooking(@Query("userId") userId: String, @Query("productId") productId: String, @Query("branchId") branchId: String): Response<Unit>
}