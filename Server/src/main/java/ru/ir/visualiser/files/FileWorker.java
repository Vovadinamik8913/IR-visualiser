package ru.ir.visualiser.files;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWorker {

    public static void createPath(String path, String foldersOrder) {
        String[] folders = foldersOrder.split("/");
        String newPath = path;
        for (String folder : folders) {

            addFolder(newPath, folder);
            newPath += File.separator + folder;
        }
    }

    public static void createPaths(String path, String[] foldersOrders) {
        for (String foldersOrder : foldersOrders) {
            createPath(path, foldersOrder);
        }

    }

    public static String getFolderName(String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    public static String absolutePath(String path) {
        String absolutePath = Config.getInstance().getWorkPath();
        if (!path.isEmpty()) {
            absolutePath += File.separator + path;
        }
        return absolutePath;
    }

    public static void addFolder(String path, String folderName) {
        File newDir = new File(absolutePath(path) + File.separator + folderName);
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
    }

    public static void addFolders(String path, String[] foldersName) {
        for (String folder : foldersName) {
            addFolder(path, folder);
        }
    }

    public static void addFile(String path, String fileName) throws IOException {
        File newFile = new File(absolutePath(path) + File.separator + fileName);
        newFile.createNewFile();
    }

    public static void cleanDirectory(String path) throws IOException {
        File dir = new File(absolutePath(path));
        if (dir.exists()) {
            FileUtils.cleanDirectory(dir);
        }
    }

    public static void copy(String path, String fileName, byte[] bytes) throws IOException {
        File file = new File(absolutePath(path) + File.separator + fileName);
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
    }
}
