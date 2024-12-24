package ru.ir.visualiser.parser;

import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final Map<Optional<String>, BlockIR> labelToBlock = new java.util.HashMap<>();
    @Getter
    private final int startLine;
    @Getter
    private final int endLine;

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public void addBlock(BlockIR block) {
        labelToBlock.put(block.getLabel(), block);
    }

    /**
     * Get block by label.
     *
     * @param label - label
     *
     * @return - block
     */
    public BlockIR getBlock(Optional<String> label) {
        BlockIR block = labelToBlock.get(label);
        if (block == null) {
            return labelToBlock.get(Optional.empty());
        }
        return labelToBlock.get(label);
    }

    public Collection<BlockIR> getBlocks() {
        return labelToBlock.values();
    }

}
