package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.ir.visualiser.files.FileWorker;
import ru.ir.visualiser.parser.FunctionIR;
import ru.ir.visualiser.parser.ModuleIR;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import ru.ir.visualiser.files.model.Ir;
import ru.ir.visualiser.files.model.IrService;

@RestController
@RequestMapping("/fromline")
public class LineToSvgController {
    @Autowired
    private IrService irService;

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
            @Parameter(description = "Ключ") @RequestParam("id") Long id
            ) {
        Ir ir = irService.getById(id);
        ModuleIR module = ir.getModule();

        Collection<FunctionIR> functions = module.getFunctions();
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

        String path = ir.getSvgPath() + "/." + function.getFunctionName() + ".svg";
        try {
            Path pathFile = Paths.get(path);
            return Files.readAllBytes(pathFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
