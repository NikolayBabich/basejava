package ru.javaops.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File directory = new File("./");
        walkAllFilesInDirectory(directory, 0);
    }

    private static void walkAllFilesInDirectory(File folder, int indentCount) {
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isHidden()) {
                String indent = "-".repeat(indentCount);
                if (fileEntry.isDirectory()) {
                    System.out.println(indent + "Directory: " + fileEntry.getName());
                    walkAllFilesInDirectory(fileEntry, indentCount + 3);
                } else {
                    System.out.println(indent + "File: " + fileEntry.getName());
                }
            }
        }
    }
}
