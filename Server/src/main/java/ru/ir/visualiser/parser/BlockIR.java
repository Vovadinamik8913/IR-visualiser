package ru.ir.visualiser.parser;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;


/**
 * Class for blockIr.
 *
 */
public class BlockIR {
    private final String textRaw;
    @Getter
    private final Optional<String> label;
    @Getter
    private final int startLine;
    @Getter
    private final int endLine;

    BlockIR(Optional<String> label, String textRaw, int startLine, int endLine) {
        this.label = label;
        this.textRaw = textRaw;
        this.startLine = startLine;
        this.endLine = endLine;
    }

}
