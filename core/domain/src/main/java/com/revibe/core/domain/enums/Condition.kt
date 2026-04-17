package com.revibe.core.domain.enums

enum class Condition(val displayName: String) {
    EXCELLENT("Идеальное"),
    GOOD("Хорошее"),
    ACCEPTABLE("Удовлетворительное");

    companion object {
        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}