package ru.ir.visualiser;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Dot {
    private final String irsPath;
    private final String builderPath;
    private String fileName;

    /**
     * Creates a new Opt object.
     *
     */
    public Dot() throws IOException {
        fileName = "";
        irsPath = Objects.requireNonNull(getClass().getResource("/ir")).getPath();
        builderPath = new ClassPathResource("build_dot.py").getFile().getPath();
    }

    /**
     * Generates dot file from LLVM IR file.
     *
     * @return True if dot file was generated successfully, false otherwise
     * @throws IOException if an I/O error occurs when executing command
     */
    public boolean generateDotFiles() throws IOException {
        String[] runtime = {"python3", builderPath};
        Process process = Runtime.getRuntime().exec(runtime);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Interrupted while waiting for process to finish");
        }
        return process.exitValue() == 0;
    }


    private void copy(File file, byte[] bytes) throws IOException {
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
    }

    private void cleanDirectory() throws IOException {
        File svgDir = new ClassPathResource("svg_files").getFile();
        if (svgDir.exists()) {
            FileUtils.cleanDirectory(svgDir);
        }
        File dotDir = new ClassPathResource("dot_files").getFile();
        if (svgDir.exists()) {
            FileUtils.cleanDirectory(dotDir);
        }
    }

    private void change() throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        String content = Files.readString(Path.of(builderPath), charset);
        int ind = content.indexOf("/ir/")+4;
        String name = content.substring(ind);
        name = name.substring(0, name.indexOf("\""));
        System.out.println(name + "\n" + fileName);
        content = content.replaceAll(name, fileName);
        Files.writeString(Path.of(builderPath), content, charset);
        cleanDirectory();
    }


    /** readfile and save to resources.
     *
     * @param file file to save
     * @return true or false
     * @throws IOException if can`t create file
     * @throws NullPointerException if null
     */
    public boolean readFile(MultipartFile file) throws IOException, NullPointerException {
        if (!file.isEmpty()) {
            fileName = file.getOriginalFilename();
            File newFile = new File(irsPath + "/" + fileName);
            if (newFile.createNewFile()) {
                copy(newFile, file.getBytes());
                change();
                System.out.println("File is created!");
                return true;
            } else {
                System.out.println("File already exists.");
                copy(newFile, file.getBytes());
                change();
                return true;
            }
        }
        return false;
    }
}
