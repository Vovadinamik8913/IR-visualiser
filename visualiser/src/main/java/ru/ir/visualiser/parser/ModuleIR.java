package ru.ir.visualiser.parser;

import java.util.ArrayList;
import java.util.List;

public class ModuleIR {
    private String moduleName;
    private String moduleTextRaw;
    private List<FunctionIR> functions = new ArrayList<>();

    ModuleIR(String moduleName, String moduleTextRaw) {
        this.moduleName = moduleName;
        this.moduleTextRaw = moduleTextRaw;
        this.functions = new ArrayList<>();
    }

    public void addFunction(FunctionIR function) {
        functions.add(function);
    }

    public List<FunctionIR> getFunctions() {
        return functions;
    }
}
