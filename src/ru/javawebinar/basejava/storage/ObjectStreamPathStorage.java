package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    protected ObjectStreamPathStorage(String dir) {
        super(dir);
    }


    @Override
    protected void doWrite(Resume r, Path path) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(path));
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(Path path) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
