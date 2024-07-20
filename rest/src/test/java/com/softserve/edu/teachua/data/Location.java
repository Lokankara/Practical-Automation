package com.softserve.edu.teachua.data;

interface IName {
    ICity setName(String name);
}

interface ICity {
    IAddress setCity(Cities city);
}

interface IAddress {
    ILatitude setAddress(String address);
}

interface ILatitude {
    ILongitude setLatitude(String latitude);
}

interface ILongitude {
    IPhoneLocation setLongitude(String longitude);
}

interface IPhoneLocation {
    IBuildLocation setPhoneNumber(String phoneNumber);
}

interface IBuildLocation {
    IBuildLocation setDistrict(String district);
    IBuildLocation setBusTrain(String busTrain);
    ILocation build();
}

public class Location implements IName, ICity, IAddress, ILatitude,
        ILongitude, IPhoneLocation, IBuildLocation, ILocation {

    private String name;
    private Cities city;
    private String district;     // optional
    private String busTrain;     // optional
    private String address;
    private String latitude;
    private String longitude;
    private String phoneNumber;

    private Location() {
        district = "";
        busTrain = "";
    }

    public static IName get() {
        return new Location();
    }

    // setters

    public ICity setName(String name) {
        this.name = name;
        return this;
    }

    public IAddress setCity(Cities city) {
        this.city = city;
        return this;
    }

    public IBuildLocation setDistrict(String district) {
        this.district = district;
        return this;
    }

    public IBuildLocation setBusTrain(String busTrain) {
        this.busTrain = busTrain;
        return this;
    }

    public ILatitude setAddress(String address) {
        this.address = address;
        return this;
    }

    public ILongitude setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public IPhoneLocation setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public IBuildLocation setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ILocation build() {
        return this;
    }

    // getters

    public String getName() {
        return name;
    }

    public Cities getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getBusTrain() {
        return busTrain;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
