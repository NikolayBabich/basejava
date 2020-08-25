package ru.javaops.basejava.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File directory = new File("./");
        walkAllFilesInDirectory(directory);
    }

    private static void walkAllFilesInDirectory(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() && !fileEntry.isHidden()) {
                walkAllFilesInDirectory(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
}
