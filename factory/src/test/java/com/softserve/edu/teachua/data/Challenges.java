package com.softserve.edu.teachua.data;

import com.softserve.edu.teachua.pages.challenge.ChallengePage;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.challenge.ChallengeUnitedPage;

public enum Challenges {
    DEFAULT_CHALLENGE("Навчайся", ChallengeTeachPage.class),
    TO_LEARN_CHALLENGE("Навчайся", ChallengeTeachPage.class),
    THE_ONLY_CHALLENGE("Єдині", ChallengeUnitedPage.class);

    private final String name;
    private final Class<? extends ChallengePage> pageClass;

    Challenges(String name, Class<? extends ChallengePage> pageClass) {
        this.name = name;
        this.pageClass = pageClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends ChallengePage> getPageClass() {
        return pageClass;
    }
}
