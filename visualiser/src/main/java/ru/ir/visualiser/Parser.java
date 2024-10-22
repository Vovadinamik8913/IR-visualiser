package ru.ir.visualiser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


    public ModuleIR parseModule(String input) {
        String moduleID = "";
        Pattern patternName = Pattern.compile("; ModuleID = (.*)");
        Matcher matcher = patternName.matcher(input);
        if(matcher.find()) {
            moduleID = matcher.group(1);
        }
        Pattern patternFunctions = Pattern.compile("(; Function Attrs: .*\\n)?(define[\\s\\S]*?\\}|declare.*\\n)");
        matcher = patternFunctions.matcher(input);

        while (matcher.find()) {
            parseFunction(matcher.group());
        }
        //ДОБАВИТЬ ДЛЯ ФУНКЦИИ в лист и в билдер
        return new ModuleIR(moduleID, input);
    }

    public FunctionIR parseFunction(String input) {
        return null;
    }

    public BlockIR parseBlock(String input) {
        return null;
    }
}
