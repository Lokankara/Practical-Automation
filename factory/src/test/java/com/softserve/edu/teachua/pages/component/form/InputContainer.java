package com.softserve.edu.teachua.pages.component.form;

import java.util.HashMap;
import java.util.Map;

public class InputContainer {
    private Map<String, InputComponent> inputs;

    public InputContainer() {
        this.inputs = new HashMap<>();
    }

    public void add(String id, InputComponent input) {
        this.inputs.put(id, input);
    }

    public InputComponent get(String id) {
        return this.inputs.get(id);
    }

    public boolean contains(String id) {
        return this.inputs.containsKey(id);
    }

    public void remove(String id) {
        this.inputs.remove(id);
    }

    public Map<String, InputComponent> getAllInputs() {
        return inputs;
    }
}
