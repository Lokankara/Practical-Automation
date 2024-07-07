package com.softserve.edu.teachua.pages.component.option.factory;

import com.softserve.edu.teachua.exception.DropdownOptionNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OptionFactory<T> {
    private final Map<String, Consumer<T>> optionHandlers = new HashMap<>();

    public OptionFactory(OptionHandler<T>[] options) {
        Arrays.stream(options).forEach(this::registerOption);
    }

    private void registerOption(OptionHandler<T> option) {
        optionHandlers.put(option.getOptionName(), option.getHandler());
    }

    public void selectOption(T component, DropdownOption<T> option) {
        Consumer<T> handler = optionHandlers.get(option.getOptionName());
        if (handler == null) {
            throw new DropdownOptionNotFoundException("Unknown option:" + option.getOptionName());
        }
        handler.accept(component);
    }
}
