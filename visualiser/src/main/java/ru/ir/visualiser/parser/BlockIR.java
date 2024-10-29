package ru.ir.visualiser.parser;

public class BlockIR {
    private boolean initial;
    private String textRow;
    private String labelIR;
    private String svgID;

    BlockIR(boolean initial, String textRow) {
        this.initial = initial;
        this.textRow = textRow;
    }


    public void setLabelIR(String labelIR) {
        this.labelIR = labelIR;
    }

    /**
     * Label of Block in the IR function that is part of the IR syntax.
     */
    public String label() {
        throw new UnsupportedOperationException();
    }

    /**
     * Id of the SVG element that represents this block.
     */
    public String svgId() {
        throw new UnsupportedOperationException();
    }
}
