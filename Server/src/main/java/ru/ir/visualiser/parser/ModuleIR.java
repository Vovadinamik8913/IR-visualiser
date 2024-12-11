package ru.ir.visualiser.parser;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.*;

/**
 * Class that holds a ModuleIr
 * with some info about it.
 */
@Entity
@Table(name = "module")
public class ModuleIR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    private String moduleName;
    private String moduleTextRaw;
    private Map<String, FunctionIR> NameToFunctions = new HashMap<>();
    private Map<FunctionIR, String> FunctionsToName = new HashMap<>();

    ModuleIR(String moduleName, String moduleTextRaw) {
        this.moduleName = moduleName;
        this.moduleTextRaw = moduleTextRaw;
        this.NameToFunctions = new HashMap<>();
        this.FunctionsToName = new HashMap<>();
    }

    public void addNameToFunction(String name, FunctionIR function) {
        NameToFunctions.putIfAbsent(name, function);
    }

    public FunctionIR getFunction(String name) {
        return NameToFunctions.get(name);
    }

    public void addFunctionToName(String name, FunctionIR function) {
        FunctionsToName.putIfAbsent(function, name);
    }

    public String getNameByFunction(FunctionIR function) {
        return FunctionsToName.get(function);
    }

    public Collection<FunctionIR> getFunctions() {
        return NameToFunctions.values();
    }
}
