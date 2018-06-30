package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class MainFile {

    private static final String DIR = "./src/";

    private static final String INDENT = "|--";

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File(DIR);
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

      //  printFilesRecursively(dir);
        printDirectoriesResursively(dir);

    }

    private static void printFilesRecursively(File dir) {
        File[] files = dir.listFiles();

        if (files == null)
            return;

        for (File file : files) {
            if (file.isFile()) {
                System.out.println("file : " + file.getName());
            }
            else if (file.isDirectory()) {
                printFilesRecursively(file);
            }
        }
    }

    private static void printDirectoriesResursively(File dir) {
        printDirectoriesResursively(dir , 0);
    }

    private static void printDirectoriesResursively(File dir, int level) {
        File[] files = dir.listFiles();

        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                String indent = String.join("", Collections.nCopies(level, INDENT));
                System.out.println(indent + file.getName());
                printDirectoriesResursively(file, level+1);
            }
        }
    }

}
