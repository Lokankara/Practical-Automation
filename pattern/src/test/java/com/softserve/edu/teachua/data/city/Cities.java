package com.softserve.edu.teachua.data.city;

public enum Cities {
    DEFAULT_CITY("Київ"),
    KYIV_CITY("Київ"),
    HARKIV_CITY("Харків");

    private final String city;

    private Cities(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return city;
    }
}
