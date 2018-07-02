package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest  {

    public PathStorageTest() {
        super(new PathStorage(SORAGE_DIR_STRING, new ObjectStreamSerializer()));
    }

}