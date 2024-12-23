package ru.ir.visualiser.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

/**
 * Class that holds dot representation of IR.
 * Holds svg ids mappings to label.
 */
@RequiredArgsConstructor
public class Dot {
    @Getter
    private final String functionName;
    private final Map<String, String> svgIdToLabel = new java.util.HashMap<>();
    private final Map<String, String> labelToSvgId = new java.util.HashMap<>();

    public void addSvgIdToLabel(String svgId, String label) {
        svgIdToLabel.put(svgId, label);
        labelToSvgId.put(label, svgId);
    }

    /**
     * Method to get label by svg id
     *
     * @param svgId - svg id
     *
     * @return - label
     */
    public String getLabelBySvgId(String svgId) {
        return svgIdToLabel.get(svgId);
    }

    /**
     * Method to get svg id by label
     *
     * @param label - label
     *
     * @return - svg id
     */
    public String getSvgIdByLabel(Optional<String> label) {
        if (label.isEmpty()) {
            String smallestLabel = null;
            for (String labelNow : labelToSvgId.keySet()) {
                if (smallestLabel == null || Integer.decode(labelToSvgId.get(labelNow)) < Integer.decode(labelToSvgId.get(smallestLabel))) {
                    smallestLabel = labelNow;
                }
            }
            return smallestLabel;
        }
        return labelToSvgId.get(label.get());
    }
}
