package com.softserve.edu.teachua.data;

public enum Challenges {
    DEFAULT_CHALLENGE("Навчайся"),
    TO_LEARN_CHALLENGE("Навчайся"),
    THE_ONLY_CHALLENGE("Єдині");

    private final String name;

    private Challenges(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
