package ru.ir.visualiser.files.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ir")
@Getter
public class Ir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String flags;
    private String filename;
    @Setter
    private String dotPath;
    @Setter
    private String svgPath;
    @Setter
    private String irPath;
    @ManyToOne @JoinColumn(name = "parent_id")
    private Ir parent;
    @OneToMany(mappedBy = "parent")
    private List<Ir> children;
//    @Setter
//    @OneToOne @JoinColumn(name = "id")
//    private ModuleIR module;

    public Ir(String filename) {
        this.flags = "init";
        this.filename = filename;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public Ir(Ir parent, String flags) {
        this.parent = parent;
        flags = flags.substring(flags.lastIndexOf("-passes=") + "-passes=".length());
        this.irPath = parent.getIrPath() + File.separator + flags;
        this.svgPath = irPath + File.separator + "svg_files";
        this.dotPath = irPath + File.separator + "dot_files";
        this.flags = flags;
        this.filename = flags + ".ll";
        this.children = new ArrayList<>();
    }

    public Ir() {

    }
}
