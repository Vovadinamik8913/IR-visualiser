package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ir.visualiser.files.model.Ir;
import ru.ir.visualiser.files.model.IrService;
import ru.ir.visualiser.parser.FunctionIR;
import ru.ir.visualiser.parser.ModuleIR;
import java.util.Collection;

/**
 * Controller that starts parsing and maps lines to function names.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/fromline")
public class LineToSvgController {
    private final IrService irService;

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
            @Parameter(description = "Id of ir") @RequestParam("file") Long id
            ) {
        Ir ir = irService.getById(id);
        ModuleIR module = ir.getModule();

        Collection<FunctionIR> functions = module.getFunctions();
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
