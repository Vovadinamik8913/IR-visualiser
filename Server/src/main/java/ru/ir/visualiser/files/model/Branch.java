package ru.ir.visualiser.files.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Branch {
    private final Map<String, Node> leaves; // String - name of opt

    public Branch() {
        leaves = new HashMap<>();
    }

    public boolean containsNode(String name) {
        return leaves.containsKey(name);
    }

    @Nullable
    public Node getNode(@NotNull String opt) {
        return leaves.get(opt);
    }


    public void addNode(@NotNull String opt, @NotNull String  filename) {
        Leaf leaf = new Leaf(0, filename);
        Node node = new Node(leaf);
        leaves.putIfAbsent(opt, node);
    }

    @Getter
    public static class Node {
        private int count;
        private final Leaf head;

        public Node(Leaf head) {
            this.count = 1;
            this.head = head;
        }

        public void addLeaf(@NotNull int id, @NotNull Leaf leaf) {
            leaf.setId(count++);
            Leaf l = head.getLeaf(id);
            if (l != null) {
                l.addChild(leaf);
            }
        }

        @Nullable
        public Leaf getLeaf(@NotNull int id) {
            return head.getLeaf(id);
        }

        public void deleteLeaf(@NotNull int id) {
            Leaf leaf = head.getLeaf(id);
            if (leaf != null) {
                leaf.delete();
            }
        }
    }
}
