package ru.javawebinar.basejava;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MainFileVisitor extends SimpleFileVisitor<Path> {

    private static final String DIR = "./src/ru/javawebinar/basejava";

    public static void main(String[] args) {
        Path path = Paths.get(DIR);

        try {
            Files.walkFileTree(path, new MainFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path,
                                             BasicFileAttributes fileAttributes) {
        System.out.println("Directory name:" + path);
        return FileVisitResult.CONTINUE;
    }

}
