package com.softserve.edu.teachua.wraps.search;

import com.softserve.edu.teachua.tools.PropertiesUtils;

public final class SearchStrategy {
    private static Search search;
    private static Strategies currentStrategy;
    private static Strategies previousStrategy;

    static {
        initSearch();
    }

    private SearchStrategy() {
        initSearch();
    }

    private static void initSearch() {
        String propertyStrategy = PropertiesUtils.get().readSearchStrategy();
        if (propertyStrategy.equals(PropertiesUtils.ERROR_READ_PROPERTY)) {
            setDefaultStrategy();
        } else {
           setStrategy(getStrategyByPartialName(propertyStrategy));
        }
    }

    private static Strategies getStrategyByPartialName(String strategyName) {
        Strategies strategy = Strategies.DEFAULT_STRATEGY;
        strategyName = strategyName.toLowerCase()
                .replaceAll("[_-]", " ")
                .replaceAll("[ ]+", " ")
                .trim();
        for (Strategies current : Strategies.values()) {
            String currentName = current.name().toLowerCase().replace("_", " ");
            if (currentName.contains(strategyName)) {
                strategy = current;
                break;
            }
        }
        return strategy;
    }

    public static Search setDefaultStrategy() {
        return setStrategy(Strategies.DEFAULT_STRATEGY);
    }

    public static Search setImplicitStrategy() {
        return setStrategy(Strategies.IMPLICIT_STRATEGY);
    }

    public static Search setExplicitPresentStrategy() {
        return setStrategy(Strategies.EXPLICIT_PRESENT_STRATEGY);
    }

    public static Search setExplicitVisibleStrategy() {
        return setStrategy(Strategies.EXPLICIT_VISIBLE_STRATEGY);
    }

    public static Search setExplicitExistText() {
        return setStrategy(Strategies.EXPLICIT_EXIST_TEXT_STRATEGY);
    }

    public static Search restoreStrategy() {
        if (previousStrategy == null) {
            return getSearch();
        }
        return setStrategy(previousStrategy);
    }

    public static Search setStrategy(Strategies strategy) {
        if (currentStrategy == null) {
            currentStrategy = strategy;
        }
        previousStrategy = currentStrategy;
        currentStrategy = strategy;
        search = strategy.getStrategy();
        return search;
    }

    public static Search getSearch() {
        if (search == null) {
            setStrategy(Strategies.DEFAULT_STRATEGY);
            //search = Strategies.DEFAULT_STRATEGY.getStrategy();
        }
        return search;
    }
}
