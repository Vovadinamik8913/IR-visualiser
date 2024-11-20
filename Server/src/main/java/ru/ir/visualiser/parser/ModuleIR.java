package ru.ir.visualiser.parser;

import java.util.*;

public class ModuleIR {
    private String moduleName;
    private String moduleTextRaw;
    private Map<String,FunctionIR> NameToFunctions = new HashMap<>();
    private Map<FunctionIR, String> FunctionsToName = new HashMap<>();

    ModuleIR(String moduleName, String moduleTextRaw) {
        this.moduleName = moduleName;
        this.moduleTextRaw = moduleTextRaw;
        this.NameToFunctions = new HashMap<>();
        this.FunctionsToName = new HashMap<>();
    }

    public void addNameToFunction(String name,FunctionIR function) {
        NameToFunctions.putIfAbsent(name, function);
    }

    public FunctionIR getFunction(String name) {
        return NameToFunctions.get(name);
    }

    public void addFunctionToName(String name,FunctionIR function) {
        FunctionsToName.putIfAbsent(function, name);
    }

    public String getNameByFunction(FunctionIR function) {
        return FunctionsToName.get(function);
    }

    public String getModuleName() {
        return moduleName;
    }

    public Collection<FunctionIR> getFunctions() {
        return NameToFunctions.values();
    }
}
