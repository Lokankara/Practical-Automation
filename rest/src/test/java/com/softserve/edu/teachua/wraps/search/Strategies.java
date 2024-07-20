package com.softserve.edu.teachua.wraps.search;

//
// Factory Method Pattern
//

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

class ExplicitStrategyExistText implements Strategy {
    public Search getStrategy() {
        return new SearchExplicitExistText();
    }
}

class ExplicitStrategyExistFirstText implements Strategy {
    public Search getStrategy() {
        return new SearchExplicitExistFirstText();
    }
}

class ExplicitStrategyInvisibily implements Strategy {
    public Search getStrategy() {
        return new SearchExplicitInvisibily();
    }
}

public enum Strategies {
    DEFAULT_STRATEGY(new ImplicitStrategy()),
    IMPLICIT_STRATEGY(new ImplicitStrategy()),
    EXPLICIT_PRESENT_STRATEGY(new ExplicitStrategyPresent()),
    EXPLICIT_VISIBLE_STRATEGY(new ExplicitStrategyVisible()),
    EXPLICIT_EXIST_TEXT_STRATEGY(new ExplicitStrategyExistText()),
    EXPLICIT_EXIST_FIRST_TEXT_STRATEGY(new ExplicitStrategyExistFirstText()),
    EXPLICIT_INVISIBILY_STRATEGY(new ExplicitStrategyInvisibily());

    private Strategy strategy;

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
