package com.softserve.edu.teachua.data.club;

import com.softserve.edu.teachua.data.city.CityRepository;

public final class ClubRepository {
    private static volatile ClubRepository instance = null;

    private ClubRepository() {
    }

    public static ClubRepository get() {
        if (instance == null) {
            synchronized (ClubRepository.class) {
                if (instance == null) {
                    instance = new ClubRepository();
                }
            }
        }
        return instance;
    }

    public IClub itEducationClub() {
        return Club.get()
                .setCity(CityRepository.get().kyivCity())
                .setTitle("IT освіта: курси \"ГРАНД\"")
                .setDescriptions("Ми вивчаємо все, що можна уявити в ІТ")
                .build();
    }

    public IClub defaultClub() {
        return Club.get()
                .setCity(CityRepository.get().defaultCity())
                .setTitle("Новий Кадр")
                .setDescriptions("Новий кадр — це справжній творчий майданчик")
                .build();
    }

    public IClub newCadreClub() {
        return Club.get()
                .setCity(CityRepository.get().harkivCity())
                .setTitle("Новий Кадр")
                .setDescriptions("Новий кадр — це справжній творчий майданчик")
                .build();
    }

    public IClub vectorClub() {
        return Club.get()
                .setCity(CityRepository.get().harkivCity())
                .setTitle("Центр позашкільної освіти \"ВЕКТОР\" Харківської міської ради")
                .setDescriptions("Центр пропонує заняття у гуртках")
                .build();
    }
    public IClub sevenClub() {
        return Club.get()
                .setCity(CityRepository.get().harkivCity())
                .setTitle("Комунальний заклад «Центр дитячої та юнацької творчості № 7 Харківської міської ради»")
                .setDescriptions("Комунальний заклад «Центр дитячої та юнацької творчості № 7 Харківської міської ради»")
                .build();
    }

    public IClub spasClub() {
        return Club.get()
                .setCity(CityRepository.get().defaultCity())
                .setTitle("Київська федерація \"Спас\". Аматорська спортивна команда")
                .setDescriptions("Київська федерація \"Спас\". Аматорська спортивна команда")
                .build();
    }
}
