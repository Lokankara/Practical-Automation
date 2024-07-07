package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;

public abstract class BaseModal {

    protected Search search;

    public BaseModal() {
        search = SearchStrategy.getSearch();
    }
}
