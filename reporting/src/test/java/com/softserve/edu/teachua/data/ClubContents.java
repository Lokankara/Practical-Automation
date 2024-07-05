package com.softserve.edu.teachua.data;

public enum ClubContents {
    DEFAULT_CLUB(Cities.HARKIV_CITY, "Новий Кадр", "Новий кадр — це справжній творчий  майданчик"),
    IT_EDUCATION_CLUB(Cities.KYIV_CITY, "IT освіта: курси \"ГРАНД\"", "Ми вивчаємо все, що можна уявити в ІТ"),
    NEW_CADRE_CLUB(Cities.HARKIV_CITY, "Новий Кадр", "Новий кадр — це справжній творчий  майданчик"),
    VECTOR_CLUB(Cities.HARKIV_CITY, "Центр позашкільної освіти \"ВЕКТОР\" Харківської міської ради",
            "Центр пропонує заняття у гуртках");

    private Cities city;
    private String title;
    private String descriptions;

    private ClubContents(Cities city, String title, String descriptions) {
        this.city = city;
        this.title = title;
        this.descriptions = descriptions;
    }

    public Cities getCity() {
        return city;
    }

    public String getTitle() {
        return title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    @Override
    public String toString() {
        return title;
    }
}
