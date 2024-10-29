package ru.ir.visualiser.parser;

import java.util.ArrayList;
import java.util.List;

public class FunctionIR {
    private String functionName;
    private List<String> parameters;
    private String functionTextRaw;
    private List<BlockIR> blocks;



    public FunctionIR(String functionName, String functionTextRaw) {
        this.functionName = functionName;
        this.parameters = new ArrayList<>();
        this.functionTextRaw = functionTextRaw;
        this.blocks = new ArrayList<>();

    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public void addBlock(BlockIR block) {
        blocks.add(block);
    }

    public List<BlockIR> getBlocks() {
        return blocks;
    }
    /**
     * Whole text of a function.
     */
    public String rawText() {
        return functionTextRaw;
    }
}
