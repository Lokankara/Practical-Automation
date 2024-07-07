package com.softserve.edu.teachua.wraps.search;

interface Strategy {
    Search getStrategy();
}

class ImplicitStrategy implements Strategy {
    public Search getStrategy() {
        return new SearchImplicit();
    }
}

class ExplicitStrategyPresent implements Strategy {
    public Search getStrategy() {
        return new SearchExplicitPresent();
    }
}

class ExplicitStrategyVisible implements Strategy {
    public Search getStrategy() {
        return new SearchExplicitVisible();
    }
}

public enum Strategies {
    DEFAULT_STRATEGY(new ImplicitStrategy()),
    IMPLICIT_STRATEGY(new ImplicitStrategy()),
    EXPLICIT_PRESENT_STRATEGY(new ExplicitStrategyPresent()),
    EXPLICIT_VISIBLE_STRATEGY(new ExplicitStrategyVisible());

    private final Strategy strategy;

    private Strategies(Strategy strategy) {
        this.strategy = strategy;
    }

    public Search getStrategy() {
        return strategy.getStrategy();
    }

    @Override
    public String toString() {
        return name();
    }
}
