package ru.ir.visualiser.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import ru.ir.visualiser.FunctionIR;

public class FunctionMapper {

    private class BlockMapper {

        private final String blockLabel;
        private final int lineFrom;
        int lineTo;

        public BlockMapper(String blockLabel, int lineFrom, int lineTo) {
            this.blockLabel = blockLabel;
            this.lineFrom = lineFrom;
            this.lineTo = lineTo;
        }
    }

    // Sorted by lineFrom
    private final List<BlockMapper> blockMappers;
    private final HashMap<String, Integer> labelsToLine;

    public FunctionMapper(FunctionIR function) {
        String text = function.rawText();
        Iterator<String> lines = text.lines().iterator();

        blockMappers = new ArrayList<>();
        labelsToLine = new HashMap<>();
        blockMappers.add(new BlockMapper("0", 0, 0));
        labelsToLine.put("0", 0);

        int line = 0;
        while (lines.hasNext()) {
            String lineText = lines.next();
            line++;

            int lineIndex = 0;
            while (lineIndex < lineText.length() && lineText.charAt(lineIndex) == ' ') {
                lineIndex++;
            }
            if (lineIndex >= lineText.length() || lineText.charAt(lineIndex) == ';') {
                lineText = lines.next();
                line++;
                lineIndex = 0;

                while (lineIndex < lineText.length() && lineText.charAt(lineIndex) != ':') {
                    lineIndex++;
                }
                if (lineIndex < lineText.length() && lineText.charAt(lineIndex) == ':') {
                    int labelLength = lineIndex;
                    lineIndex++;
                    while (lineIndex < lineText.length() && lineText.charAt(lineIndex) == ' ') {
                        lineIndex++;
                    }
                    if (lineIndex >= lineText.length() || lineText.charAt(lineIndex) == ';') {
                        String label = lineText.substring(0, labelLength);
                        labelsToLine.put(label, line);
                        blockMappers.get(blockMappers.size() - 1).lineTo = line;
                        blockMappers.add(new BlockMapper(label, line, line));
                    }
                }
            }
        }
        blockMappers.get(blockMappers.size() - 1).lineTo = line;
    }

    /**
     * @param line Line number relative to the start of the function
     * @return Block label. 0 if it is the first block in the function.
     * @throws IndexOutOfBoundsException if line is outside of the function
     */
    public String blockLabelFromLine(int line) throws IndexOutOfBoundsException {
        if (line < 0 || line >= blockMappers.get(blockMappers.size() - 1).lineTo) {
            throw new IndexOutOfBoundsException();
        }
        int min = 0;
        int max = blockMappers.size();

        int index = (min + max) / 2;

        while (blockMappers.get(index).lineFrom < line || blockMappers.get(index).lineTo > line) {
            if (blockMappers.get(index).lineFrom < line) {
                min = index + 1;
            } else {
                max = index;
            }
            index = (min + max) / 2;
        }
        return blockMappers.get(index).blockLabel;
    }

    /**
     *
     * @param blockLabel Block label. If label is not found, returns the ine of
     * the first block. @retur First line of the code block. Relative to the
     * start of the function
     * @throws IndexOutOfBoundsException if blockId is outside of the fun tion
     */
    public int lineFromBlockLabel(String blockLabel) throws IndexOutOfBoundsException {
        if (!labelsToLine.containsKey(blockLabel)) {
            return labelsToLine.get("0");
        }
        return labelsToLine.get(blockLabel);
    }
}
