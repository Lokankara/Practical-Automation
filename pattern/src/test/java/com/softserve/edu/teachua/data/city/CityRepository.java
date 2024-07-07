package com.softserve.edu.teachua.data.city;

public final class CityRepository {
    private static volatile CityRepository instance = null;

    public static CityRepository get() {
        if (instance == null) {
            synchronized (CityRepository.class) {
                if (instance == null) {
                    instance = new CityRepository();
                }
            }
        }
        return instance;
    }

    public ICity harkivCity() {
        return City.get().setCity("Харків").build();
    }

    public ICity defaultCity() {
        return City.get().setCity("Київ").build();
    }

    public ICity kyivCity() {
        return City.get().setCity("Київ").build();
    }
}
