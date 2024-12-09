package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ir.visualiser.files.Config;
import ru.ir.visualiser.files.FileWorker;
import ru.ir.visualiser.files.llvm.Opt;
import ru.ir.visualiser.files.llvm.Svg;
import ru.ir.visualiser.files.model.Ir;
import ru.ir.visualiser.files.model.IrService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/files")
public class MainController {
    @Autowired
    private IrService irService;

    public MainController() {
        irService = new IrService();
    }

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
        ir.setIrPath(path + "/ir_files/" + folderName);
        ir.setSvgPath(path + "/svg_files/" + folderName);
        ir.setDotPath(path + "/dot_files/" + folderName);
        irService.create(ir);

        FileWorker.createPath(path, folder + File.separator + folderName);
        FileWorker.createPaths(path,
                new String[]{
                        "dot_files/"+folderName,
                        "ir_files/"+folderName,
                        "svg_files/"+folderName
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
    @PostMapping(value = "/build/path")
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
    @PostMapping(value = "/build/file")
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

    @PostMapping(value = "/get/functions")
    @ResponseBody
    public List<String> getSvgs(
            @Parameter(description = "Id of ir", required = true) @RequestParam("file") Long id
    ) {
        Ir ir = irService.getById(id);
        List<String> list = new ArrayList<>();
        File file = new File(ir.getSvgPath());
        if (file.exists()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                String name = f.getName();
                list.add(name.substring(name.indexOf(".") + 1, name.lastIndexOf(".")));
            }
        }
        return list;
    }

    @Operation(summary = "Получение svg по имени функции")
    @PostMapping(value = "/get/svg")
    @ResponseBody
    public ResponseEntity<byte[]> getSvg(
            @Parameter(description = "Id of ir", required = true) @RequestParam("file") Long id,
            @Parameter(description = "Function name", required = true) @RequestParam("function") String functionName
    ) {
        Ir ir = irService.getById(id);
        String path = ir.getSvgPath() + "/." + functionName + ".svg";
        try {
            Path dirPath = Paths.get(path);
            return ResponseEntity.ok(Files.readAllBytes(dirPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ofNullable(null);
    }

}
