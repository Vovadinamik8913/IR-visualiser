package ru.ir.visualiser.files.model;

import lombok.Getter;

@Getter
public class State {
    private final Tree tree;

    public State() {
        tree = new Tree();
    }

}
