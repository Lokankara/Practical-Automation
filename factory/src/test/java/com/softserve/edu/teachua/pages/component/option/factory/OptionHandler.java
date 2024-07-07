package com.softserve.edu.teachua.pages.component.option.factory;

import java.util.function.Consumer;

public interface OptionHandler<T> extends DropdownOption<T> {

    Consumer<T> getHandler();
}
