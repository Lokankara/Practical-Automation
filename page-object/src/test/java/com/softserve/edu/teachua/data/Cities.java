package com.softserve.edu.teachua.data;

public enum Cities {
    DEFAULT_CITY("Київ"),
    KYIV_CITY("Київ"),
    HARKIV_CITY("Харків"),
    DNIPRO_CITY("Дніпро"),
    ODESA_CITY("Одеса"),
    ZAPORIZHZHIA_CITY("Запоріжжя"),
    LVIV_CITY("Львів"),
    TEST5_CITY("Test5"),
    TEST3_CITY("Test3"),
    TEST7_CITY("Test7"),
    TEST4_CITY("Test4"),
    TEST9_CITY("Test9"),
    ONLINE_CITY("Без локації");

    private final String city;

    Cities(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return getCity();
    }
}
