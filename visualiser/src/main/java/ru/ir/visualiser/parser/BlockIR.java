package ru.ir.visualiser.parser;

public class BlockIR {
    private boolean initial;
    private String textRow;
    private String labelIR;
    private String svgID;
    private int startLine;
    private int endLine;

    BlockIR(boolean initial, String textRow, int startLine, int endLine) {
        this.initial = initial;
        this.textRow = textRow;
        this.startLine = startLine;
        this.endLine = endLine;
    }


    public void setLabelIR(String labelIR) {
        this.labelIR = labelIR;
    }

    public void setSvgID(String svgID) {
        this.svgID = svgID;
    }

    public int getStartLine(int startLine) {
        return startLine;
    }
    public int getEndLine(int endLine) {
        return endLine;
    }

    /**
     * Label of Block in the IR function that is part of the IR syntax.
     */
    public String label() {
        return labelIR;
    }

    /**
     * Id of the SVG element that represents this block.
     */
    public String svgId() {
        return svgID;
    }
}
