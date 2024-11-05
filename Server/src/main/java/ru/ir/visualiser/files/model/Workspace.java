package ru.ir.visualiser.files.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Workspace {
    private final Map<String, State> states; // String - ukey

    public Workspace() {
        states = new HashMap<>();
    }

    public boolean containsState(String key) {
        return states.containsKey(key);
    }

    public void addState(@NotNull String key) {
        states.putIfAbsent(key, new State());
    }

    @Nullable
    public State getState(@NotNull String key) {
        return states.get(key);
    }

}
