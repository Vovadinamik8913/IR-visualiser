package ru.ir.visualiser.files.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Tree {
    private final Map<String, Branch> children; // String - name of file

    public Tree() {
        children = new HashMap<>();
    }

    public void addBranch(@NotNull String filename) {
        children.putIfAbsent(filename, new Branch());
    }

    public boolean containsBranch(@NotNull String filename) {
        return children.containsKey(filename);
    }

    @Nullable
    public Branch getBranch(@NotNull String fileName) {
        return children.get(fileName);
    }
}
