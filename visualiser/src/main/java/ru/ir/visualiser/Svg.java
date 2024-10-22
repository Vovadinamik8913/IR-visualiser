package ru.ir.visualiser;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class Svg {
    private final String builderPath;

    public Svg() throws IOException {
        builderPath = new ClassPathResource("build_svg.py").getFile().getPath();
    }


    private void cleanDirectory() throws IOException {
        File svgDir = new ClassPathResource("svg_files").getFile();
        if (svgDir.exists()) {
            FileUtils.cleanDirectory(svgDir);
        }
    }

    public boolean generateSvgFiles() throws IOException {
        cleanDirectory();
        String[] runtime = {"python3", builderPath};
        Process process = Runtime.getRuntime().exec(runtime);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Interrupted while waiting for process to finish");
        }
        return process.exitValue() == 0;
    }
}
