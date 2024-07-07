package com.softserve.edu.teachua.wraps.search;

import com.softserve.edu.teachua.tools.PropertiesUtils;
import com.softserve.edu.teachua.tools.ReportUtils;

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
            ReportUtils.logInfo(PropertiesUtils.ERROR_READ_PROPERTY + " Using default search strategy.");
            setDefaultStrategy();
        } else {
            Strategies strategy = getStrategyByPartialName(propertyStrategy);
            ReportUtils.logInfo("Setting strategy to: " + strategy.name());
            setStrategy(strategy);
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
        ReportUtils.logInfo("Setting default search strategy: " + Strategies.DEFAULT_STRATEGY);
        return setStrategy(Strategies.DEFAULT_STRATEGY);
    }

    public static Search setImplicitStrategy() {
        ReportUtils.logInfo("Setting implicit search strategy: " + Strategies.IMPLICIT_STRATEGY);
        return setStrategy(Strategies.IMPLICIT_STRATEGY);
    }

    public static Search setExplicitPresentStrategy() {
        ReportUtils.logInfo("Setting explicit present search strategy: " + Strategies.EXPLICIT_PRESENT_STRATEGY);
        return setStrategy(Strategies.EXPLICIT_PRESENT_STRATEGY);
    }

    public static Search setExplicitVisibleStrategy() {
        ReportUtils.logInfo("Setting explicit visible search strategy: " + Strategies.EXPLICIT_VISIBLE_STRATEGY);
        return setStrategy(Strategies.EXPLICIT_VISIBLE_STRATEGY);
    }

    public static Search setExplicitExistText() {
        ReportUtils.logInfo("Setting explicit exist text search strategy: " + Strategies.EXPLICIT_EXIST_TEXT_STRATEGY);
        return setStrategy(Strategies.EXPLICIT_EXIST_TEXT_STRATEGY);
    }

    public static Search restoreStrategy() {
        ReportUtils.logInfo("Restoring previous search strategy: " + (previousStrategy != null ? previousStrategy.name() : "None"));
        if (previousStrategy == null) {
            return getSearch();
        }
        return setStrategy(previousStrategy);
    }

    public static Search setStrategy(Strategies strategy) {
        ReportUtils.logInfo("Setting search strategy to: " + strategy.name());
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
        }
        ReportUtils.logInfo("Getting search strategy: " + search);
        return search;
    }
}
