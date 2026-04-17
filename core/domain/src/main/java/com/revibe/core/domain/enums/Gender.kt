package com.revibe.core.domain.enums

enum class Gender(val displayName: String) {
    MALE("Мужское"),
    FEMALE("Женское"),
    UNISEX("Унисекс");

    companion object {
        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}