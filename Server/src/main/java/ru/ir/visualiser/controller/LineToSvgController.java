package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ir.visualiser.files.FileWorker;
import ru.ir.visualiser.parser.FunctionIR;
import ru.ir.visualiser.parser.ModuleIR;
import ru.ir.visualiser.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@RestController
@RequestMapping("/fromline")
public class LineToSvgController {
    Parser parser = new Parser();
    ModuleIR current;
    boolean parsed = false;


    /**
     * Parses .ll file into ModuleIr, FunctionIR, BlockIR
     *
     * @param file - .ll file to be parsed
     *
     * @return - String, representing a file containment
     *
     * @throws IOException - if cant access file
     */
    @Operation(summary = "initial parsing of .ll")
    @PostMapping(value = "/parse")
    public String parse(
            @Parameter(description = ".ll file", required = true) @RequestParam("file") MultipartFile file
    ) throws IOException {
        String fileContent = "";
        if (!parsed) {
            fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            current = parser.parseModule(fileContent);
            parsed = true;
        }
        return fileContent ;
    }


    /**
     *Method to get svg from line
     *
     * @param line - line number in .ll file
     * @param folder - folder of current working project
     *
     * @return - bytes, representing needed IR Function
     */
    @Operation(summary = "send svg corresponding to a line")
    @PostMapping(value = "/get/svg")
    @ResponseBody
    public byte[] getSvg(
            @Parameter(description = "Номер строки") @RequestParam("line") int line,
            @Parameter(description = "Ключ") @RequestParam("folder") String folder
            ) {
        if(current == null) {
            System.out.println("Nothing parsed yet");
            return null;
        }
        Collection<FunctionIR> functions = current.getFunctions();
        FunctionIR function = null;
        for(FunctionIR functionNow : functions) {
            if(functionNow.getStartLine() <= line && functionNow.getEndLine() >= line) {
                function = functionNow;
            }
        }
        if(function == null) {
            System.out.println("Function not found");
            return null;
        }

        String path = FileWorker.absolutePath("") + File.separator + folder + File.separator + folder +
                "/svg_files/" + folder + "/." + function.getFunctionName() + ".svg";
        try {
            Path pathFile = Paths.get(path);
            return Files.readAllBytes(pathFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
