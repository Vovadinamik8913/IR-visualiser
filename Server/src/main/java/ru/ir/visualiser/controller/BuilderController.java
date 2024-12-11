package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.ir.visualiser.files.Config;
import ru.ir.visualiser.files.FileWorker;
import ru.ir.visualiser.files.llvm.Opt;
import ru.ir.visualiser.files.llvm.Svg;
import ru.ir.visualiser.files.model.Ir;
import ru.ir.visualiser.files.model.IrService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import ru.ir.visualiser.parser.ModuleIR;
import ru.ir.visualiser.parser.Parser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files/build")
public class BuilderController {
    private final IrService irService;

    private void generate(String optPath, Ir ir) throws IOException {
        if (Opt.validateOpt(optPath)) {
            if (Opt.generateDotFiles(
                    optPath,
                    ir.getDotPath(),
                    ir.getFilename()
            )) {
                Svg.generateSvgFiles(
                        ir.getDotPath(),
                        ir.getSvgPath()
                );
            }
        }
    }

    private Ir create(String folder, int opt, String filename, byte[] content) throws IOException {
        String folderName = FileWorker.getFolderName(filename);
        String path = FileWorker.absolutePath(folder + File.separator + folderName);
        String optPath = Config.getInstance().getOptsPath()[opt];

        Ir ir = new Ir(filename);
        ir.setIrPath(path);
        ir.setSvgPath(path + "/svg_files");
        ir.setDotPath(path + "/dot_files");

        ModuleIR module = Parser.parseModule(new String(content, StandardCharsets.UTF_8));
        ir.setModule(module);

        irService.create(ir, module);

        FileWorker.createPaths(path,
                new String[]{
                        "dot_files",
                        "svg_files"
                }
        );
        FileWorker.copy(ir.getIrPath(),
                filename,
                content
        );
        generate(optPath, ir);
        return ir;
    }

    @Operation(summary = "Создание svg и dot по пути до ir файла на сервере")
    @PostMapping(value = "/path")
    public ResponseEntity<Long> buildSVGByPath(
            @Parameter(description = "Folder", required = true) @RequestParam("folder") String folder,
            @Parameter(description = "Opt", required = true) @RequestParam("opt") int opt,
            @Parameter(description = "Path of file", required = true) @RequestParam("filePath") String filePath
    ) {
        try {
            File file = new File(filePath);
            String filename = file.getName();
            Ir ir = create(folder, opt, filename, Files.readAllBytes(file.toPath()));
            return ResponseEntity.ok(ir.getId());
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ofNullable(null);
    }

    @Operation(summary = "Создание svg и dot по переданному файлу")
    @PostMapping(value = "/file")
    public ResponseEntity<Long> buildSVGByFile(
            @Parameter(description = "Folder", required = true) @RequestParam("folder") String folder,
            @Parameter(description = "Opt", required = true) @RequestParam("opt") int opt,
            @Parameter(description = "File to load", required = true) @RequestParam("file") MultipartFile file
    ) {
        try {
            String filename = file.getOriginalFilename();
            Ir ir = create(folder, opt, filename, file.getBytes());
            return ResponseEntity.ok(ir.getId());
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ofNullable(null);
    }
}
