package ru.ir.visualiser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ir.visualiser.files.Config;
import ru.ir.visualiser.files.FileWorker;
import ru.ir.visualiser.files.llvm.Opt;
import ru.ir.visualiser.files.llvm.Svg;
import ru.ir.visualiser.files.model.Branch;
import ru.ir.visualiser.files.model.State;
import ru.ir.visualiser.files.model.Workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/llvm")
public class MainController {
    Workspace workspace;

    public MainController() {
        workspace = new Workspace();
    }

    @Operation(summary = "Отправка файла")
    @PostMapping(value = "/build")
    public ResponseEntity<String> buildSVG(
            @Parameter(description = "Ключ") @RequestParam("key") String key,
            @Parameter(description = "Имя opt") @RequestParam("opt") int opt,
            @Parameter(description = "Файл для загрузки", required = true) @RequestParam("file") MultipartFile file
    ) {
        try {
            String filename = file.getOriginalFilename();
            String folder = FileWorker.getFolderName(filename);
            String path = key + File.separator + folder;
            if (!workspace.containsState(key)) {
                workspace.addState(key);
            }
            State state = workspace.getState(key);
            if(!state.getTree().containsBranch(filename)) {
                state.getTree().addBranch(filename);
            }

            Branch branch = state.getTree().getBranch(filename);
            String optPath = Config.getInstance().getOptsPath()[opt];
            if (!branch.containsNode(optPath)) {
                branch.addNode(optPath, filename);
            }
            FileWorker.createPath(
                    "",
                    path
            );
            FileWorker.createPaths(path,
                    new String[]{
                            "dot_files/"+folder,
                            "ir_files/"+folder,
                            "svg_files/"+folder
                    }
            );
            FileWorker.addFile(path+"/ir_files/"+folder,
                    filename
            );
            FileWorker.copy(path+"/ir_files/"+folder,
                    filename,
                    file.getBytes()
            );
            if (Opt.validateOpt(optPath)) {
                if (Opt.generateDotFiles(
                        optPath,
                        FileWorker.absolutePath(path+"/dot_files/"+folder),
                        filename
                )) {
                    Svg.generateSvgFiles(
                            FileWorker.absolutePath(path + "/dot_files/" + folder),
                            FileWorker.absolutePath(path + "/svg_files/" + folder)
                    );
                }
            }
            return ResponseEntity.ok("ok");
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return (ResponseEntity<String>) ResponseEntity.badRequest();
    }

    @PostMapping(value = "/get/svgs")
    @ResponseBody
    public List<String> getSvgs(
            @Parameter(description = "Ключ") @RequestParam("key") String key,
            @Parameter(description = "Имя файла") @RequestParam("filename") String filename
    ) {
        String folder = FileWorker.getFolderName(filename);
        String path = key + File.separator + folder + "/svg_files/" + folder;
        List<String> list = new ArrayList<>();
        File file = new File(FileWorker.absolutePath(path));
        if (file.exists()) {
            for (File f : file.listFiles()) {
                String name = f.getName();
                list.add(name.substring(name.indexOf(".") + 1, name.lastIndexOf(".")));
            }
        }
        return list;
    }

    @PostMapping(value = "/get/svg")
    @ResponseBody
    public byte[] getSvg(
            @Parameter(description = "Ключ") @RequestParam("key") String key,
            @Parameter(description = "Имя файла") @RequestParam("filename") String filename,
            @Parameter(description = "Имя функции") @RequestParam("svgname") String svgname
    ) {
        String folder = FileWorker.getFolderName(filename);
        String path = key + File.separator + folder + "/svg_files/" + folder +
                "/." + svgname + ".svg";
        try {
            Path dirPath = Paths.get(FileWorker.absolutePath(path));
            byte[] byteArray = Files.readAllBytes(dirPath);
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
