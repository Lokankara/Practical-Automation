package com.softserve.edu.teachua.data.club;

import com.softserve.edu.teachua.data.LocationRepository;

public class ClubRepository {

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

    public IClub defaultClub() {
        return americanGymnastics();
    }

    public IClub americanGymnastics() {
        return Club.get()
                .setName("American Gymnastics Club")
                .addCategories("Спортивні секції")
                .setAgeMin(0)
                .setAgeMax(16)
                .setLocation(LocationRepository.get().kyiv1())
                .setOnline(false)
                .addWorkDate(WeekDays.MONDAY, WorkTime.get()
                        .setBeginHour("10")
                        .setBeginMinute("00")
                        .setEndHour("18")
                        .setEndMinute("00")
                        .build())
                .addWorkDate(WeekDays.THURSDAY, WorkTime.get()
                        .setBeginHour("10")
                        .setBeginMinute("00")
                        .setEndHour("18")
                        .setEndMinute("00")
                        .build())
                .setPhoneNumber("044333710111")
                .setDescription("Американський гімнастичний клуб (American Gymnastics Club) – перша та єдина в країні мережа унікальних спортивних клубів")
                .build();
    }

    public IClub newCadr() {
        return Club.get()
                .setName("Новий Кадр")
                .addCategories("Журналістика")
                .setAgeMin(6)
                .setAgeMax(16)
                .setLocation(LocationRepository.get().harkiv1())
                .setOnline(false)
                .addWorkDate(WeekDays.MONDAY, WorkTime.get()
                        .setBeginHour("10")
                        .setBeginMinute("00")
                        .setEndHour("18")
                        .setEndMinute("00")
                        .build())
                .addWorkDate(WeekDays.THURSDAY, WorkTime.get()
                        .setBeginHour("10")
                        .setBeginMinute("00")
                        .setEndHour("18")
                        .setEndMinute("00")
                        .build())
                .setPhoneNumber("044333710111")
                .setDescription("Новий кадр — це справжній творчий майданчик")
                .setCoverUrl("https://www.voopty.com.ua/studio/NovyKadr")
                .build();
    }

    public IClub vector() {
        return Club.get()
                .setName("Центр позашкільної освіти \"ВЕКТОР\" Харківської міської ради")
                .addCategories("Художня студія")
                .addCategories("Програмування")
                .setAgeMin(4)
                .setAgeMax(16)
                .setLocation(LocationRepository.get().harkiv1())
                .setOnline(false)
                .addWorkDate(WeekDays.MONDAY, WorkTime.get()
                        .setBeginHour("10")
                        .setBeginMinute("00")
                        .setEndHour("18")
                        .setEndMinute("00")
                        .build())
                .addWorkDate(WeekDays.THURSDAY, WorkTime.get()
                        .setBeginHour("10")
                        .setBeginMinute("00")
                        .setEndHour("18")
                        .setEndMinute("00")
                        .build())
                .setPhoneNumber("044333710111")
                .setDescription("Центр пропонує заняття у гуртках: авіамоделювання, початкове технічне моделювання")
                .setCoverUrl("http://vektor.klasna.com/")
                .build();
    }

}
