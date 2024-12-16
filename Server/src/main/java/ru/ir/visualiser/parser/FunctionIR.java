package ru.ir.visualiser.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Class that holds a FunctionIr
 * with some info about it.
 */
@RequiredArgsConstructor
public class FunctionIR {
    @Getter
    private final String functionName;
    private final List<String> parameters = new java.util.ArrayList<>();
    @Getter
    private final String functionTextRaw;
    @Getter
    private final List<BlockIR> blocks = new java.util.ArrayList<>();
    @Getter
    private final int startLine;
    @Getter
    private final int endLine;

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public void addBlock(BlockIR block) {
        blocks.add(block);
    }

}
