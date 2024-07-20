package com.softserve.edu.teachua.data.club;

interface IBeginHour {
    IBeginMinute setBeginHour(String beginHour);
}

interface IBeginMinute {
    IEndHour setBeginMinute(String beginMinute);
}

interface IEndHour {
    IEndMinute setEndHour(String endHour);
}

interface IEndMinute {
    IBuildWorkTime setEndMinute(String endMinute);
}

interface IBuildWorkTime {
    IWorkTime build();
}

public class WorkTime implements IBeginHour, IBeginMinute,
        IEndHour, IEndMinute, IBuildWorkTime, IWorkTime {

    private String beginHour;
    private String beginMinute;
    private String endHour;
    private String endMinute;

    private WorkTime() {
    }

    public static IBeginHour get() {
        return new WorkTime();
    }

    // setters

    public IBeginMinute setBeginHour(String beginHour) {
        this.beginHour = beginHour;
        return this;
    }

    public IEndHour setBeginMinute(String beginMinute) {
        this.beginMinute = beginMinute;
        return this;
    }

    public IEndMinute setEndHour(String endHour) {
        this.endHour = endHour;
        return this;
    }

    public IBuildWorkTime setEndMinute(String endMinute) {
        this.endMinute = endMinute;
        return this;
    }

    public IWorkTime build() {
        return this;
    }

    // getters

    public String getBeginHour() {
        return beginHour;
    }

    public String getBeginMinute() {
        return beginMinute;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }
}
