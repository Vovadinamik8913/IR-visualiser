package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ir.visualiser.files.model.Ir;
import ru.ir.visualiser.files.model.IrService;
import ru.ir.visualiser.parser.FunctionIR;
import ru.ir.visualiser.parser.ModuleIR;
import ru.ir.visualiser.parser.Parser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;


/**
 * Controller that starts parsing and maps lines to function names.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/fromline")
public class LineToSvgController {
    private final IrService irService;
    Parser parser = new Parser();
    ModuleIR current;
    String filename;

    /**
     * Parses .ll file into ModuleIr, FunctionIR, BlockIR
     *
     * @param id - id of .ll file to be parsed
     *
     * @return - String, respresenting result of the operation
     *
     */
    @Operation(summary = "initial parsing of .ll")
    @PostMapping(value = "/parse")
    public String parse(
            @Parameter(description = ".ll file id", required = true) @RequestParam("id") Long id
    ) {
        try {
            Ir ir = irService.getById(id);
            Path path = new File(ir.getIrPath() + File.separator + ir.getFilename()).toPath();
            String content = Files.readString(path, StandardCharsets.UTF_8);
            filename = String.valueOf(path.getFileName());
            current = parser.parseModule(content);
            return "Parsed " + filename;
        } catch (Exception e) {
            return  "Something went wrong";
        }

    }


    /**
     *Method to get svg from line
     *
     * @param line - line number in .ll file
     * @param folder - folder of current working project
     *
     * @return - String, name of the function
     */
    @Operation(summary = "send svg corresponding to a line")
    @PostMapping(value = "/get/svg")
    @ResponseBody
    public String getSvg(
            @Parameter(description = "Номер строки") @RequestParam("line") int line,
            @Parameter(description = "Ключ") @RequestParam("folder") String folder
            ) {
        if (current == null) {
            System.out.println("Nothing parsed yet");
            return null;
        }
        Collection<FunctionIR> functions = current.getFunctions();
        FunctionIR function = null;
        for (FunctionIR functionNow : functions) {
            if (functionNow.getStartLine() <= line && functionNow.getEndLine() >= line) {
                function = functionNow;
            }
        }
        if (function == null) {
            System.out.println("Function not found");
            return null;
        }

        return function.getFunctionName();
    }
}
