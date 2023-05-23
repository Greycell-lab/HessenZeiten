package org.hessenzeiten;

public enum Monat {
    JANUAR("Januar", 1),
    FEBRUAR("Februar", 2),
    MÄRZ("März", 3),
    APRIL("April", 4),
    MAI("Mai", 5),
    JUNI("Juni", 6),
    JULI("Juli", 7),
    AUGUST("August", 8),
    SEPTEMBER("September", 9),
    OKTOBER("Oktober", 10),
    NOVEMBER("November", 11),
    DEZEMBER("Dezember", 12);
    private final String name;
    private final int num;

    Monat(String name, int num){
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }
}
