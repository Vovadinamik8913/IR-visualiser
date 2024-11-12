package ru.ir.visualiser.files.llvm;

import ru.ir.visualiser.files.FileWorker;

import java.io.File;
import java.io.IOException;

public class Opt {
    public static boolean generateDotFiles(String opt, String dotPath, String filename) throws IOException {
        File dotDir = new File(dotPath);
        ProcessBuilder processBuilder = new ProcessBuilder(
                opt,
                "-passes=dot-cfg",
                "../../ir_files/" + FileWorker.getFolderName(filename) + "/" + filename);
        processBuilder.directory(dotDir);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException("Interrupted while waiting for process to finish");
        }
        return process.exitValue() == 0;
    }

    public static boolean validateOpt(String opt) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(new String[] {opt, "--version"});
            boolean result = process.waitFor(1, java.util.concurrent.TimeUnit.SECONDS);
            process.destroyForcibly();
            return result;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

}
