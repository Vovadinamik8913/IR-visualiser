package ru.ir.visualiser.files.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


public class Leaf {
    @Getter
    @Setter
    private int id;
    @Getter
    private final String optimization;
    @Getter
    private final String filename;
    @Setter
    @Getter
    private Leaf parent;
    private final Map<Integer, Leaf> children;

    public Leaf(int id, String filename) {
        this.id = id;
        this.optimization = "init";
        this.filename = filename;
        this.parent = null;
        this.children = new HashMap<>();
    }

    public Leaf(int id, String optimization, String filename) {
        this.id = id;
        this.optimization = optimization;
        this.filename = filename;
        this.parent = null;
        this.children = new HashMap<>();
    }

    public void addChild(@NotNull Leaf leaf) {
        children.put(leaf.getId(), leaf);
        leaf.setParent(this);
    }

    public void delete() {
        parent.children.remove(this.id);
    }

    @Nullable
    public Leaf getLeaf(@NotNull int id) {
        if (this.id == id) {
            return this;
        }
        if (children.isEmpty()) {
            return null;
        }
        if (children.containsKey(id)) {
            return children.get(id);
        } else {
            Leaf res = null;
            for (Leaf leaf : children.values()) {
                res = leaf.getLeaf(id);
                if (res != null) {
                    return res;
                }
            }
            return null;
        }
    }

    public String getFolderName() {
        return filename.substring(0, filename.lastIndexOf('.'));
    }
}
