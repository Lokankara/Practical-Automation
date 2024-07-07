package com.softserve.edu.teachua.data.city;

interface IBuildCity {
    ICity build();
}

interface ICityName {
    IBuildCity setCity(String cityName);
}

public class City implements ICityName, IBuildCity, ICity {
    private String cityName;

    public static ICityName get() {
        return new City();
    }

    @Override
    public ICity build() {
        return this;
    }

    @Override
    public IBuildCity setCity(String cityName) {
        this.cityName = cityName;
        return this;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String toString() {
        return getCityName();
    }
}
