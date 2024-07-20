package com.softserve.edu.teachua.data.club;

import com.softserve.edu.teachua.data.ILocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface INameClub {
    ICategories setName(String name);
}

interface ICategories {
    IAgeMin addCategories(String category);
}

interface IAgeMin extends ICategories {
    IAgeMax setAgeMin(int ageMin);
}

interface IAgeMax {
    ICenterLocation setAgeMax(int ageMax);
}

interface ICenterLocation {
    IIsOnline setCenterName(String centerName);
    IIsOnline setLocation(ILocation location);
}

interface IIsOnline {
    IWorkDate setOnline(boolean online);
}

interface IWorkDate {
    IPhoneClub addWorkDate(WeekDays weekDays, IWorkTime workTime);
}

interface IPhoneClub extends IWorkDate {
    IDescription setPhoneNumber(String phoneNumber);
}

interface IDescription {
    IBuildClub setDescription(String description);
}

interface IBuildClub {
    IBuildClub setFacebook(String facebook);
    IBuildClub setViber(String viber);
    IBuildClub setEmail(String email);
    IBuildClub setSkype(String skype);
    IBuildClub setWeb(String web);
    IBuildClub setLogoUrl(String logoUrl);
    IBuildClub setCoverUrl(String coverUrl);
    IBuildClub setGalleryUrl(String galleryUrl);
    IClub build();
}

public class Club implements INameClub, ICategories, IAgeMin, IAgeMax,
        ICenterLocation, IIsOnline, IWorkDate, IPhoneClub, IDescription, IBuildClub, IClub {
    private String name;
    private List<String> categories;
    private int ageMin;
    private int ageMax;
    private String centerName;      // optional
    private ILocation location;
    private boolean isOnline;
    private Map<WeekDays, IWorkTime> workDate;
    private String phoneNumber;
    private String facebook;        // optional
    private String viber;           // optional
    private String email;           // optional
    private String skype;           // optional
    private String web;             // optional
    private String logoUrl;         // optional
    private String coverUrl;        // optional
    private String galleryUrl;      // optional
    private String description;

    private Club() {
        categories = new ArrayList<>();
        workDate = new HashMap<>();
    }

    public static INameClub get() {
        return new Club();
    }

    // setters

    public ICategories setName(String name) {
        this.name = name;
        return this;
    }

    public IAgeMin addCategories(String category) {
        categories.add(category);
        return this;
    }

    public IAgeMax setAgeMin(int ageMin) {
        this.ageMin = ageMin;
        return this;
    }

    public ICenterLocation setAgeMax(int ageMax) {
        this.ageMax = ageMax;
        return this;
    }

    public IIsOnline setCenterName(String centerName) {
        this.centerName = centerName;
        return this;
    }

    public IIsOnline setLocation(ILocation location) {
        this.location = location;
        return this;
    }

    public IWorkDate setOnline(boolean online) {
        isOnline = online;
        return this;
    }

    public IPhoneClub addWorkDate(WeekDays weekDays, IWorkTime workTime) {
        workDate.put(weekDays, workTime);
        return this;
    }

    public IDescription setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public IBuildClub setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public IBuildClub setViber(String viber) {
        this.viber = viber;
        return this;
    }

    public IBuildClub setEmail(String email) {
        this.email = email;
        return this;
    }

    public IBuildClub setSkype(String skype) {
        this.skype = skype;
        return this;
    }

    public IBuildClub setWeb(String web) {
        this.web = web;
        return this;
    }

    public IBuildClub setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public IBuildClub setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public IBuildClub setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
        return this;
    }

    public IBuildClub setDescription(String description) {
        this.description = description;
        return this;
    }

    public IClub build() {
        return this;
    }

    // getters

    public String getName() {
        return name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public String getCenterName() {
        return centerName;
    }

    public ILocation getLocation() {
        return location;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Map<WeekDays, IWorkTime> getWorkDate() {
        return workDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getViber() {
        return viber;
    }

    public String getEmail() {
        return email;
    }

    public String getSkype() {
        return skype;
    }

    public String getWeb() {
        return web;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getGalleryUrl() {
        return galleryUrl;
    }

    public String getDescription() {
        return description;
    }
}
