package com.revibe.feature.login.data.dto

data class UserDto(
    val id: String,
    val email: String,
    val fullName: String,
    val role: String,
    val bonusPoints: Int
)