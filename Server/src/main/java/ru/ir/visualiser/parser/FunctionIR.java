package ru.ir.visualiser.parser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds a FunctionIr
 * with some info about it.
 */
public class FunctionIR {
    @Getter
    private final String functionName;
    private final List<String> parameters;
    @Getter
    private final String functionTextRaw;
    @Getter
    private final List<BlockIR> blocks;
    @Getter
    private int startLine;
    @Getter
    private int endLine;



    public FunctionIR(String functionName, String functionTextRaw, int startLine, int endLine) {
        this.functionName = functionName;
        this.parameters = new ArrayList<>();
        this.functionTextRaw = functionTextRaw;
        this.blocks = new ArrayList<>();
        this.startLine = startLine;
        this.endLine = endLine;

    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public void addBlock(BlockIR block) {
        blocks.add(block);
    }

}
