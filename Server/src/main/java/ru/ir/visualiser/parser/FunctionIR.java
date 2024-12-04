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
    private String functionName;
    private List<String> parameters;
    @Getter
    private String functionTextRaw;
    @Getter
    private List<BlockIR> blocks;
    @Getter
    @Setter
    private int startLine;
    @Getter
    @Setter
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
