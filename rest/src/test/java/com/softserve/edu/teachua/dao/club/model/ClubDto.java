package com.softserve.edu.teachua.dao.club.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClubDto(
        int id,
        int ageFrom,
        int ageTo,
        String name,
        String description,
        String urlWeb,
        String urlLogo,
        String urlBackground,
        List<CategoryDto> categories,
        List<LocationDto> locations,
        int feedbackCount,
        List<ContactDto> contacts,
        Double rating
) {
}

record LocationDto(
        int id,
        String name,
        String address,
        String cityName,
        String districtName,
        String stationName,
        int cityId,
        Integer districtId,
        int stationId,
        Integer centerId,
        int clubId,
        Double longitude,
        Double latitude,
        String phone,
        String coordinates
) {
}

record ContactDto(
        ContactTypeDto contactType,
        String contactData
) {
}

record ContactTypeDto(
        int id,
        String name,
        String urlLogo
) {
}
