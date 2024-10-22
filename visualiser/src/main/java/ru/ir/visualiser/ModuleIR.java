package ru.ir.visualiser;

import java.util.ArrayList;
import java.util.List;

public class ModuleIR {
    private String moduleName;
    private String moduleTextRaw;
    List<FunctionIR> functions = new ArrayList<>();

    ModuleIR(String moduleName, String moduleTextRaw) {
        this.moduleName = moduleName;
        this.moduleTextRaw = moduleTextRaw;
    }
}
