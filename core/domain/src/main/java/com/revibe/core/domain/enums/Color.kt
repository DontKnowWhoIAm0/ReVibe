package com.revibe.core.domain.enums

enum class Color(val displayName: String) {
    WHITE("Белый"),
    BLACK("Чёрный"),
    GREY("Серый"),
    BLUE("Синий"),
    NAVY("Тёмно-синий"),
    RED("Красный"),
    GREEN("Зелёный"),
    YELLOW("Жёлтый"),
    BROWN("Коричневый"),
    BEIGE("Бежевый"),
    ORANGE("Оранжевый"),
    PURPLE("Фиолетовый"),
    PINK("Розовый"),
    TURQUOISE("Бирюзовый"),
    LIME("Салатовый"),
    OLIVE("Оливковый"),
    CORAL("Коралловый"),
    GOLD("Золотой"),
    SILVER("Серебряный"),
    BRONZE("Бронзовый"),
    MINT("Мятный"),
    LAVENDER("Лавандовый"),
    PEACH("Персиковый"),
    INDIGO("Индиго");

    companion object {
        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}