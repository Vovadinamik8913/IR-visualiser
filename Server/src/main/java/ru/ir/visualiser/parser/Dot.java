package ru.ir.visualiser.parser;

import java.util.Map;

/**
 * Class that holds dot representation of IR.
 * Holds svg ids mappings to label.
 */
public class Dot {
    private final Map<String, String> svgIdToLabel = new java.util.HashMap<>();
    private final Map<String, String> labelToSvgId = new java.util.HashMap<>();

    public void addSvgIdToLabel(String svgId, String label) {
        svgIdToLabel.put(svgId, label);
        labelToSvgId.put(label, svgId);
    }

    public String getLabelBySvgId(String svgId) {
        return svgIdToLabel.get(svgId);
    }

    public String getSvgIdByLabel(String label) {
        return labelToSvgId.get(label);
    }
}
