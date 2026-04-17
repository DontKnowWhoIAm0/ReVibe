package com.revibe.core.domain.enums

enum class Role(val displayName: String) {
    USER("Пользователь"),
    ADMIN("Администратор"),
    SUPER_ADMIN("Главный администратор");

    companion object {
        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}