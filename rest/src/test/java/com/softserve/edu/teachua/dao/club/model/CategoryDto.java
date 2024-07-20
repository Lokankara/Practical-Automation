package com.softserve.edu.teachua.dao.club.model;

public record CategoryDto(
        int id,
        int sortby,
        String name,
        String description,
        String urlLogo,
        String backgroundColor,
        String tagBackgroundColor,
        String tagTextColor
) {
}
