package com.softserve.edu.teachua.tests;

import com.softserve.edu.bootstrap.ExamplePage;
import org.junit.jupiter.api.Test;

class OtherTest extends TestRunner {

    @Test
    void test2() {
        driverWrapper.getDriver().get("https://devexpress.github.io/devextreme-reactive/react/grid/docs/guides/filtering/");
        new ExamplePage(driverWrapper.getDriver())
                .customFilter()
                .typeName("au")
                .searchResultCount(2);
    }
}
