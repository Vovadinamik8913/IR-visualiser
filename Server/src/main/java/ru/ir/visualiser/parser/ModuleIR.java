package ru.ir.visualiser.parser;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Collection;
import java.util.Map;

/**
 * Class that holds a ModuleIr
 * with some info about it.
 */
//@Entity
//@Table(name = "module")
@RequiredArgsConstructor
public class ModuleIR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;

    @Getter
    private final String moduleName;
    private final String moduleTextRaw;
    private final Map<String, FunctionIR> nameToFunctions = new java.util.HashMap<>();
    private final Map<String, Dot> nameToDot = new java.util.HashMap<>();


    public void addFunction(FunctionIR function) {
        nameToFunctions.putIfAbsent(function.getFunctionName(), function);
    }

    public void addNameToDot(String name, Dot dot) {
        nameToDot.putIfAbsent(name, dot);
    }

    public FunctionIR getFunction(String name) {
        return nameToFunctions.get(name);
    }

    public Dot getDot(String name) {
        return nameToDot.get(name);
    }

    public Collection<FunctionIR> getFunctions() {
        return nameToFunctions.values();
    }

    public Collection<Dot> getDots() {
        return nameToDot.values();
    }
}
