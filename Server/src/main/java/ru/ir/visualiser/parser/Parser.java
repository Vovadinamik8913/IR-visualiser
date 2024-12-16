package ru.ir.visualiser.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that performs parsing of llvm ir files.
 */
public class Parser {

    /**
     * Method to determine at which line char with index is located
     *
     * @param text - text to check in
     *
     * @param index - index of a char
     *
     * @return number of line
     */
    public static int getLineNumber(String text, int index) {
        int line = 1;
        for (int i = 0; i < index; i++) {
            if (text.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }

    /**
     * Method to parse whole IR module.
     * Finds name of a module.
     * Finds every function, cuts their bodies,
     * Determines range of lines for each function,
     * and calls parseFunction for each.
     *
     * @param input - Text of IR.
     *
     * @return ModuleIR
     */
    public static ModuleIR parseModule(String input) {
        String moduleID = "";
        String regexModuleName = "; ModuleID = (.*)";
        Pattern patternName = Pattern.compile(regexModuleName);
        Matcher matcher = patternName.matcher(input);
        if (matcher.find()) {
            moduleID = matcher.group(1);
        }
        String regexFunc = "(; Function Attrs: .*\\n)?(define[\\s\\S]*?}|declare.*\\n)";
        Pattern patternFunctions = Pattern.compile(regexFunc);
        matcher = patternFunctions.matcher(input);

        ModuleIR moduleIR = new ModuleIR(moduleID, input);
        ArrayList<FunctionIR> functions = new ArrayList<>();

        while (matcher.find()) {
            functions.add(parseFunction(matcher.group(), getLineNumber(input, matcher.start()), getLineNumber(input, matcher.end())));
        }

        for (FunctionIR function : functions) {
            moduleIR.addNameToFunction(function.getFunctionName(), function);
            moduleIR.addFunctionToName(function.getFunctionName(), function);
        }
        return moduleIR;
    }

    public static Dot parseDot(String input) {
        Dot dot = new Dot();
        String regexId = "(Node0x[0-9a-f]+)\\s*\\[[^]]*label=\"\\{([^:]+)";
        Pattern patternId = Pattern.compile(regexId);
        Matcher matcher = patternId.matcher(input);
        while (matcher.find()) {
            dot.addSvgIdToLabel(matcher.group(1), matcher.group(2));
        }
        return dot;
    }

    /**
     * Method to parse function.
     * Finds function name, cuts every block from function body
     * and calls parseBlock for each.
     * @param input - Function body
     *
     * @param startLine - number of first line of that function(counting from start of module).
     *
     * @param endLine - number of last line of that function(counting from start of module).
     *
     * @return FunctionIR
     */
    private static FunctionIR parseFunction(String input, int startLine, int endLine) {
        String regexFuncName = "(define|declare)[^@]* @(\\w*)";
        Pattern patternName = Pattern.compile(regexFuncName);

        Matcher matcher = patternName.matcher(input);
        String functionID = "";
        if (matcher.find()) {
            functionID = matcher.group(2);
        }

        FunctionIR functionIR = new FunctionIR(functionID, input, startLine, endLine);

        String regexBlock = "(\\{[\\s\\S]*?(?:\\n\\n|}))|(\\d+:[\\s\\S]*?(?:\\n\\n|\\n}))";
        Pattern patternBlock = Pattern.compile(regexBlock);
        matcher = patternBlock.matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                functionIR.addBlock(parseBlock(matcher.group(1), true,
                        startLine + getLineNumber(input, matcher.start()),
                        startLine + getLineNumber(input, matcher.end())));
                continue;
            }
            functionIR.addBlock(parseBlock(matcher.group(2), false,
                    startLine + matcher.start(), startLine + matcher.end()));

        }
        return functionIR;
    }

    /**
     * Method for parsing block
     *
     * @param input - body of a block
     *
     * @param initial - true if block is first in function body(don`t have name)
     *
     * @param startLine - number of first line of that block(counting from start of module).
     *
     * @param endLine - number of last line of that block(counting from start of module).
     *
     * @return - BlockIR
     */
    private static BlockIR parseBlock(String input, boolean initial, int startLine, int endLine) {
        if (initial) {
            return new BlockIR(true, input, startLine, endLine);
        }
        BlockIR blockIR = new BlockIR(false, input, startLine, endLine);
        String regexId = "(\\d+):";
        Pattern patternId = Pattern.compile(regexId);
        Matcher matcher = patternId.matcher(input);
        if (matcher.find()) {
            blockIR.setLabelIR(matcher.group(1));
        }
        return blockIR;
    }
}
