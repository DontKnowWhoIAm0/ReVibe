package com.revibe.core.domain.enums;

enum class Category(val displayName: String) {
    JACKETS("Куртки"),
    TRENCH_COATS("Плащи"),
    COATS("Пальто"),
    WINDBREAKERS("Ветровки"),
    VESTS("Жилетки"),

    SWEATERS("Свитеры"),
    LONG_SLEEVES("Лонгсливы"),
    HOODIES("Худи"),
    TURTLENECKS("Водолазки"),
    TOPS("Топы"),
    BLOUSES("Блузы"),
    T_SHIRTS("Футболки"),
    POLO_SHIRTS("Поло"),
    LONG_SLEEVE_SHIRTS("Рубашки с длинным рукавом"),
    SHORT_SLEEVE_SHIRTS("Рубашки с коротким рукавом"),

    JEANS("Джинсы"),
    PANTS("Брюки"),
    TROUSERS("Штаны"),
    LEGGINGS("Леггинсы"),
    SHORTS("Шорты"),
    SKIRTS("Юбки"),
    DRESSES("Платья"),

    SNEAKERS("Кроссовки"),
    CANVAS_SHOES("Кеды"),
    BOOTS("Ботинки"),
    SHOES("Туфли"),
    LOAFERS("Лоферы"),
    SANDALS("Сандалии"),
    BALLET_FLATS("Балетки"),

    SCARVES("Шарфы"),
    HATS("Шапки"),
    GLOVES("Перчатки"),
    BELTS("Ремни"),
    BAGS("Сумки"),
    BACKPACKS("Рюкзаки"),
    GLASSES("Очки"),
    JEWELRY("Украшения");

    companion object {
        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}
