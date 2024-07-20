package com.softserve.edu.teachua.data;

public class LocationRepository {

    private static volatile LocationRepository instance = null;

    private LocationRepository() {
    }

    public static LocationRepository get() {
        if (instance == null) {
            synchronized (LocationRepository.class) {
                if (instance == null) {
                    instance = new LocationRepository();
                }
            }
        }
        return instance;
    }

    public ILocation defaultLocation() {
        return kyiv1();
    }

    public ILocation kyiv1() {
        return Location.get()
                .setName("Локація 1")
                .setCity(Cities.KYIV_CITY)
                .setAddress("вулиця Фізкультури 1, корпус 3")
                .setLatitude("50.420219951016215")
                .setLongitude("30.508992611533227")
                .setPhoneNumber("0671234567")
                .setDistrict("Деснянський")
                .setBusTrain("Академмістечко")
                .build();
    }

    public ILocation harkiv1() {
        return Location.get()
                .setName("Локація 1")
                .setCity(Cities.HARKIV_CITY)
                .setAddress("Харків, Різдвяна 29 а")
                .setLatitude("50.420219951016215")
                .setLongitude("30.508992611533227")
                .setPhoneNumber("0664090294")
                .build();
    }
}
