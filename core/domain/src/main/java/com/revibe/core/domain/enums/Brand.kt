package com.revibe.core.domain.enums


enum class Brand(val displayName: String) {
    GUCCI("Gucci"),
    PRADA("Prada"),
    LOUIS_VUITTON("Louis Vuitton"),
    CHANEL("Chanel"),
    DIOR("Dior"),
    VERSACE("Versace"),
    BALENCIAGA("Balenciaga"),
    FENDI("Fendi"),
    BURBERRY("Burberry"),
    VALENTINO("Valentino"),
    HERMES("Hermès"),
    ALEXANDER_MCQUEEN("Alexander McQueen"),
    DOLCE_GABBANA("Dolce & Gabbana"),
    TOM_FORD("Tom Ford"),

    MICHAEL_KORS("Michael Kors"),
    RALPH_LAUREN("Ralph Lauren"),
    CALVIN_KLEIN("Calvin Klein"),
    TOMMY_HILFIGER("Tommy Hilfiger"),
    HUGO_BOSS("Hugo Boss"),
    ARMANI("Armani"),
    LACOSTE("Lacoste"),
    GUESS("Guess"),
    LEVIS("Levis"),
    MANGO("Mango"),
    ZARA("Zara"),
    H_AND_M("H&M"),
    UNIQLO("Uniqlo"),
    BERSHKA("Bershka"),
    PULL_AND_BEAR("Pull & Bear"),
    STRADIVARIUS("Stradivarius"),

    NIKE("Nike"),
    ADIDAS("Adidas"),
    PUMA("Puma"),
    REEBOK("Reebok"),
    NEW_BALANCE("New Balance"),
    ASICS("Asics"),
    FILA("Fila"),
    CHAMPION("Champion"),
    CONVERSE("Converse"),
    VANS("Vans"),
    SKECHERS("Skechers"),
    COLUMBIA("Columbia"),
    THE_NORTH_FACE("The North Face"),
    TIMBERLAND("Timberland"),
    SUPREME("Supreme"),
    OFF_WHITE("Off-White"),
    KAPPA("Kappa"),
    OBEY("Obey");

    companion object {
        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }
    }
}