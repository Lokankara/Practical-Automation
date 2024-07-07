package com.softserve.edu.teachua.data.club;

import com.softserve.edu.teachua.data.city.ICities;
import com.softserve.edu.teachua.data.city.ICity;

public class Club implements ICities, ITitle, IDescriptions, IBuildClub, IClub {

    private ICity city;
    private String title;
    private String descriptions;

    public static ICities get() {
        return new Club();
    }

    @Override
    public ITitle setCity(ICity city) {
        this.city = city;
        return this;
    }

    @Override
    public IDescriptions setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IBuildClub setDescriptions(String descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    @Override
    public IClub build() {
        return this;
    }

    @Override
    public String getCityName() {
        return city.getCityName();
    }

    @Override
    public ICity getCity() {
        return city;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescriptions() {
        return descriptions;
    }

    @Override
    public String toString() {
        return String.format("Club{city=%s, title='%s', descriptions='%s'}",
                city, title, descriptions);
    }
}
