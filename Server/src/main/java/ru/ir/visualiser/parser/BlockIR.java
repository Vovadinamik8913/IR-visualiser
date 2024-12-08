package ru.ir.visualiser.parser;
import lombok.Getter;
import lombok.Setter;


/**
 * Class for blockIr.
 *
 */
public class BlockIR {
    private boolean initial;
    private String textRaw;
    @Setter @Getter
    private String labelIR;
    @Setter @Getter
    private String svgID;
    @Getter
    private int startLine;
    @Getter
    private int endLine;

    BlockIR(boolean initial, String textRaw, int startLine, int endLine) {
        this.initial = initial;
        this.textRaw = textRaw;
        this.startLine = startLine;
        this.endLine = endLine;
    }

}
