package ru.ir.visualiser.parser;

import java.util.ArrayList;
import java.util.List;

public class FunctionIR {
    private String functionName;
    private List<String> parameters;
    private String functionTextRaw;
    private List<BlockIR> blocks;
    private int startLine;
    private int endLine;



    public FunctionIR(String functionName, String functionTextRaw, int startLine, int endLine) {
        this.functionName = functionName;
        this.parameters = new ArrayList<>();
        this.functionTextRaw = functionTextRaw;
        this.blocks = new ArrayList<>();
        this.startLine = startLine;
        this.endLine = endLine;

    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }
    public int getEndLine() {
        return endLine;
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

    public String getFunctionName() {
        return functionName;
    }
}
