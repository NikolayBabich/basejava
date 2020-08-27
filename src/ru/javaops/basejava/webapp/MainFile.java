package ru.javaops.basejava.webapp;

import java.io.File;
import java.util.Objects;

final class MainFile {
    private MainFile() {
    }

    public static void main(String[] args) {
        File directory = new File("./");
        walkAllFilesInDirectory(directory, 0);
    }

    private static void walkAllFilesInDirectory(File folder, int indentCount) {
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isHidden()) {
                String indent = "-".repeat(indentCount);
                if (fileEntry.isDirectory()) {
                    System.out.printf("%sDirectory: %s%n", indent, fileEntry.getName());
                    walkAllFilesInDirectory(fileEntry, indentCount + 3);
                } else {
                    System.out.printf("%sFile: %s%n", indent, fileEntry.getName());
                }
            }
        }
    }
}
