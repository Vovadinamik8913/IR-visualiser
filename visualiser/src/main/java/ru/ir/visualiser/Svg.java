package ru.ir.visualiser;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class Svg {
    private final String builderPath;

    public Svg() throws IOException {
        builderPath = new ClassPathResource("build_svg.py").getFile().getPath();
    }


    public boolean generateSvgFiles() throws IOException {
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
