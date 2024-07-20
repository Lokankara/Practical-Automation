package com.softserve.edu.teachua.data.club;

import com.softserve.edu.teachua.data.ILocation;

import java.util.List;
import java.util.Map;

public interface IClub {

    String getName();

    List<String> getCategories();

    int getAgeMin();

    int getAgeMax();

    String getCenterName();

    ILocation getLocation();

    boolean isOnline();

    Map<WeekDays, IWorkTime> getWorkDate();

    String getPhoneNumber();

    String getFacebook();

    String getViber();

    String getEmail();

    String getSkype();

    String getWeb();

    String getLogoUrl();

    String getCoverUrl();

    String getGalleryUrl();

    String getDescription();
}
