package com.softserve.edu.teachua.tools;

import java.util.Random;

public final class PositiveFeedbackGenerator {
    private PositiveFeedbackGenerator() {
    }

    private static final Random random = new Random();

    private static final String[] PREFIXES = {
            "Great job on",
            "Well done with",
            "Excellent work on",
            "Fantastic effort on",
            "Impressive work on",
            "Bravo on",
            "Outstanding effort on",
            "You nailed it with",
            "Awesome job on",
            "Keep up the good work on"
    };

    private static final String[] EXCLAMATIONS = {
            "Well done",
            "Congratulations",
            "Awesome",
            "Keep it up",
            "Nice",
            "Way to go",
            "Good job",
            "Great work",
            "Fantastic",
            "Superb",
            "Excellent",
            "Bravo",
            "Outstanding",
            "Impressive",
            "Brilliant",
            "Terrific",
            "Splendid",
            "Amazing",
            "Stellar",
            "Incredible",
            "Marvelous",
            "Stupendous",
            "Unbelievable",
            "Exceptional",
            "Wonderful",
            "Magnificent",
            "Astonishing"
    };

    private static final String[] POSITIVE_MIDDLES = {
            "handling",
            "managing",
            "accomplishing",
            "executing",
            "delivering",
            "completing",
            "succeeding with",
            "performing on",
            "achieving",
            "mastering"
    };

    private static final String[] POSITIVE_MODIFIERS = {
            "with exceptional",
            "with outstanding",
            "with remarkable",
            "with impressive",
            "with commendable",
            "with extraordinary",
            "with stellar",
            "with superb",
            "with exemplary",
            "with incredible"
    };

    private static final String[] POSITIVE_ACTIONS = {
            "the project",
            "your performance",
            "the task",
            "the challenge",
            "the assignment",
            "the objective",
            "the goal",
            "the milestone",
            "the endeavor",
            "the initiative"
    };


    private static final String[] POSITIVE_ADJECTIVES = {
            "Impressive",
            "Amazing",
            "Fantastic",
            "Incredible",
            "Marvelous",
            "Surprising",
            "Wonderful",
            "Astonishing",
            "Astounding",
            "Extraordinary",
            "Exceptional",
            "Stunning",
            "Remarkable",
            "Magnificent",
            "Excellent",
            "Superb",
            "Unbelievable",
            "Breathtaking",
            "Spectacular",
            "Prodigious"
    };

    private static final String[] EXCLAMATION_NOUNS = {
            "job",
            "work",
            "effort",
            "achievement",
            "performance",
            "endeavor",
            "task",
            "accomplishment",
            "activity",
            "execution",
            "duty",
            "operation",
            "project",
            "responsibility",
            "role",
            "mission",
            "action",
            "deed",
            "results",
            "outcome",
            "improvement",
            "achievement",
            "success",
            "advancement",
            "development",
            "growth",
            "effort",
            "increase",
            "elevation",
            "performance",
            "effort",
            "achievement",
            "execution",
            "success",
            "performance"
    };

    private static final String[] ADJECTIVE_NOUNS = {
            "job",
            "work",
            "effort",
            "achievement",
            "performance",
            "endeavor",
            "task",
            "accomplishment",
            "activity",
            "execution",
            "results",
            "outcome",
            "progress",
            "improvement",
            "achievement",
            "success",
            "advancement",
            "development",
            "growth",
            "performance",
            "breakthrough",
            "milestone",
            "upswing",
            "upturn",
            "step forward",
            "jump",
            "boost",
            "accomplishment",
            "progress",

    };

    public static String generateFeedback() {
        String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
        String middle = POSITIVE_MIDDLES[random.nextInt(POSITIVE_MIDDLES.length)];
        String action = POSITIVE_ACTIONS[random.nextInt(POSITIVE_ACTIONS.length)];
        String modifier = POSITIVE_MODIFIERS[random.nextInt(POSITIVE_MODIFIERS.length)];
        String adjective = POSITIVE_ADJECTIVES[random.nextInt(POSITIVE_ADJECTIVES.length)];
        String noun = EXCLAMATION_NOUNS[random.nextInt(EXCLAMATION_NOUNS.length)];
        String adjectiveNouns = ADJECTIVE_NOUNS[random.nextInt(ADJECTIVE_NOUNS.length)];
        String exclamation = EXCLAMATIONS[random.nextInt(EXCLAMATIONS.length)];

        return String.format("%s %s. %s %s %s %s. %s %s!", exclamation, noun, prefix, middle, action, modifier, adjective, adjectiveNouns);
    }
}
